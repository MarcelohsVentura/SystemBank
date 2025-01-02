package com.marceloventura.BankSystem.service.bankService;
import com.marceloventura.BankSystem.dto.request.BankResquestDTO;
import com.marceloventura.BankSystem.dto.response.BankResponseDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.entity.Client;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.repository.BankRepository;
import com.marceloventura.BankSystem.repository.ClientRepository;
import com.marceloventura.BankSystem.util.authentication.Authentication;
import com.marceloventura.BankSystem.util.validator.Validator;
import com.marceloventura.BankSystem.util.mapper.BankMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BankServiceImplements implements BankService {

    private final BankMapper bankMapper;

    private BankRepository bankRepository;

    private final Validator validator;

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public BankServiceImplements(BankMapper bankMapper, BankRepository bankRepository, Validator validator, ClientRepository clientRepository, AccountRepository accountRepository) {
        this.bankMapper = bankMapper;
        this.bankRepository = bankRepository;
        this.validator = validator;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public BankResponseDTO findById(Long id) {
        return bankMapper.toBankResponseDTO(returnBank(id));
    }

    @Override
    public List<BankResponseDTO> findAll() {
        return bankMapper.toBankResponseDTO(bankRepository.findAll());
    }

    @Override
    public String delete(Long id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bank not found"));

        validator.validateDeletableBank(bank);

        bankRepository.delete(bank);

        return "Bank ID " + id + " was deleted";
    }

    @Override
    public BankResponseDTO createBank(BankResquestDTO bankRequestDTO) {

        String agencyNumber = generateAgencyNumber();

        validator.isEmptyParametersBank(bankRequestDTO);

        Bank bank = Bank.builder()
                .bankName(bankRequestDTO.getBankName())
                .agencyNumber(bankRequestDTO.getAgencyNumber())
                .clients(new ArrayList<>())  // Inicializa como uma lista vazia
                .accounts(new ArrayList<>()) // Inicializa como uma lista vazia
                .build();

        Bank savedBank = bankRepository.save(bank);

        return bankMapper.toBankResponseDTO(savedBank);
    }

    @Override
    public Bank findBankById(Long bankId) {
        return bankRepository.findById(bankId).orElseThrow(() -> new RuntimeException("Bank not found"));
    }

    @Override
    public BankResponseDTO updateBank(Long id, BankResquestDTO bankRequestDTO) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bank wasn't found with ID " + id));

        validator.isEmptyParametersBank(bankRequestDTO);

        bank.setBankName(bankRequestDTO.getBankName());
        bank.setAgencyNumber(bankRequestDTO.getAgencyNumber());

        return bankMapper.toBankResponseDTO(bankRepository.save(bank));
    }

    @Override
    public void addClientToBank(Long bankId, Long clientId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found with ID: " + bankId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));

        bank.getClients().add(client);
        client.setBank(bank);

        bankRepository.save(bank);
    }

    @Override
    public void addAccountToBank(Long bankId, Long accountId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found with ID: " + bankId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + accountId));

        bank.getAccounts().add(account);
        account.setBank(bank);

        bankRepository.save(bank);
    }

    @Override
    public void deleteClient(Long bankId, Long clientId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found with ID: " + bankId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));

      validator.deleteClient(bankId, clientId);

        bank.getClients().remove(client);

        bankRepository.save(bank);
    }

    @Override
    public void deleteAccount (Long bankId, Long accountId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found with ID: " + bankId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + accountId));

        if (!bank.getAccounts().contains(account)) {
            throw new IllegalStateException("Client is not associate with this bank.");
        }

        bank.getAccounts().remove(account);

        bankRepository.save(bank);
    }

    private Bank returnBank(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank wasn't found on database"));
    }

    public String generateAgencyNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public Authentication getAuthentication () {
        return Authentication.getInstantia();
    }

}