package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.CheckingAccount;
import com.marceloventura.BankSystem.entity.SavingsAccount;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.util.validator.Validator;
import jakarta.persistence.EntityNotFoundException;

public class TransferProcessor implements TransactionProcessor {

    private Validator validator;

    private AccountRepository accountRepository;

    private CheckingAccount checkingAccount;

    public TransferProcessor(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void process(Account account, TransactionRequestDTO transactionRequestDTO) {

        double amount = transactionRequestDTO.getAmount();

        Long destinationAccountId = transactionRequestDTO.getDestinationAccount().getId();

        Account destinationAccount = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Destination account not found with ID: " + destinationAccountId));

        validator.validateDeposit(amount);

        validator.validateAccountForTransactions(account);

        if (account instanceof CheckingAccount) {
            double fee = amount * checkingAccount.getTransactionFee();
            account.setBalance(account.getBalance() - amount - fee);
        } else if (account instanceof SavingsAccount) {
            account.setBalance(account.getBalance() - amount);
        }

        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(account);
        accountRepository.save(destinationAccount);
    }
}
