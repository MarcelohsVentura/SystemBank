package com.marceloventura.BankSystem.service.accountService;

import com.marceloventura.BankSystem.dto.request.AccountRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.dto.response.ClientResponseDTO;
import com.marceloventura.BankSystem.entity.*;
import com.marceloventura.BankSystem.enumeration.AccountType;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.repository.ClientRepository;
import com.marceloventura.BankSystem.service.bankService.BankService;
import com.marceloventura.BankSystem.service.clientService.ClientService;
import com.marceloventura.BankSystem.util.validator.Validator;
import com.marceloventura.BankSystem.util.mapper.AccountMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AccountServiceImplements implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final Validator validator;
    private final ClientService clientService;
    private final BankService bankService;

    @Autowired
    public AccountServiceImplements(AccountMapper accountMapper, AccountRepository accountRepository, Validator validator, ClientService clientService, BankService bankService) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.validator = validator;
        this.clientService = clientService;
        this.bankService = bankService;
    }

    @Override
    public AccountResponseDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
        return accountMapper.toAccountResponseDTO(account);
    }

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        Bank bank = bankService.findBankById(accountRequestDTO.getBankId());
        Client client = clientService.findClientById(accountRequestDTO.getClientId());

        String accountNumber = generateAccountNumber();

        Account account;

        validator.isEmptyParametersAccount(accountRequestDTO);

        if (accountRequestDTO.getAccountType() == AccountType.CHECKING_ACCOUNT) {
            account = CheckingAccount.builder()
                    .balance(accountRequestDTO.getBalance())
                    .client(client)
                    .accountType(AccountType.CHECKING_ACCOUNT)
                    .accountNumber(accountNumber)
                    .bank(bank)
                    .build();
        } else if (accountRequestDTO.getAccountType() == AccountType.SAVINGS_ACCOUNT) {
            account = SavingsAccount.builder()
                    .balance(accountRequestDTO.getBalance())
                    .client(client)
                    .accountType(AccountType.SAVINGS_ACCOUNT)
                    .accountNumber(accountNumber)
                    .bank(bank)
                    .build();
        } else {
            throw new IllegalArgumentException("Unsupported account type.");
        }

       account = accountRepository.save(account);

        return accountMapper.toAccountResponseDTO(account);
    }

    @Override
    public AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO, String password) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));

        validator.validateAccountClientPassword(password, account.getClient());

        validator.validateAccountBalance(account);

        account.setBalance(accountRequestDTO.getBalance());
        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setAccountType(accountRequestDTO.getAccountType());

        account = accountRepository.save(account);
        return accountMapper.toAccountResponseDTO(account);
    }

    @Override
    public String deleteAccount(Long id, String password) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));

        validator.validateAccountClientPassword(password, account.getClient());

        validator.validateAccountBalanceToDelete(account);

        accountRepository.delete(account);
        return "Account with ID " + id + " has been deleted successfully.";
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

}




