package com.marceloventura.BankSystem.service.clientService;

import com.marceloventura.BankSystem.dto.request.ClientRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.dto.response.ClientResponseDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.entity.Client;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.repository.BankRepository;
import com.marceloventura.BankSystem.repository.ClientRepository;
import com.marceloventura.BankSystem.service.bankService.BankService;
import com.marceloventura.BankSystem.util.validator.Validator;
import com.marceloventura.BankSystem.util.mapper.AccountMapper;
import com.marceloventura.BankSystem.util.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImplements implements ClientService {

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    private final ClientMapper clientMapper;

    private final Validator validator;

    private final AccountMapper accountMapper;

    private final BankService bankService;

    private BankRepository bankRepository;

    @Autowired
    public ClientServiceImplements(BankService bankService, ClientRepository clientRepository, AccountRepository accountRepository, ClientMapper clientMapper, Validator validator, AccountMapper accountMapper, BankRepository bankRepository, AccountRepository accountRepository1) {
        this.bankService = bankService;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.validator = validator;
        this.accountMapper = accountMapper;
        this.bankRepository = bankRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public ClientResponseDTO findById(Long id) {
        return clientMapper.toClientResponseDTO(returnClient(id));
    }

    @Override
    public List<ClientResponseDTO> findAll() {
        return clientMapper.toClientResponseDTO(clientRepository.findAll());
    }

    public Client createClient(ClientRequestDTO clientRequestDTO) {
        Bank bank = bankService.findBankById(clientRequestDTO.getBankId());

        Client client = new Client();

        client.setName(clientRequestDTO.getName());
        client.setIdNumber(clientRequestDTO.getIdNumber());
        client.setPassword(clientRequestDTO.getPassword());
        client.setBank(bank);

        return clientRepository.save(client);
    }

    @Override
    public ClientResponseDTO update(Long clientId, ClientRequestDTO clientRequestDTO) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client wasn't found for ID " + clientId));

        validator.validatePassword(clientRequestDTO.getPassword(), client.getPassword());

        client.setPassword(clientRequestDTO.getPassword());
        client.setName(clientRequestDTO.getName());

        return clientMapper.toClientResponseDTO(clientRepository.save(client));
    }

    @Override
    public String delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Validator.validateDeletable(client);

        clientRepository.delete(client);

        return "Client ID " + id + " was deleted";

    }

    @Override
    public List<AccountResponseDTO> getAccountsByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));

        return client.getAccounts().stream()
                .map(accountMapper::toAccountResponseDTO)
                .collect(Collectors.toList());
    }

    private Client returnClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Person wasn't found on database"));
    }

    @Override
    public void addAccountToClient(Long bankId, Long accountId, Long clientId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new EntityNotFoundException("Bank not found with ID: " + bankId));


        Account account = accountRepository.findById(accountId)
                        .orElseThrow(()-> new EntityNotFoundException("Account not found with ID " + accountId));

        Client client = clientRepository.findById(clientId)
                        .orElseThrow(()-> new EntityNotFoundException("Client not found with ID " + clientId));

        bank.getAccounts().add(account);
        client.getAccounts().add(account);
        account.setBank(bank);
        client.setBank(bank);

        clientRepository.save(client);
        bankRepository.save(bank);
        accountRepository.save(account);
    }

    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(()-> new RuntimeException("Client not found"));
    }

}