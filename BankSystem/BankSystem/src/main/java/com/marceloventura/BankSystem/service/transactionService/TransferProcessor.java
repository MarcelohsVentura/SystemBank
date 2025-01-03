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
    public void process(Account sourceAccount, Account destinationAccount, TransactionRequestDTO transactionRequestDTO) {

        double amount = transactionRequestDTO.getAmount();

        Long destinationAccountId = transactionRequestDTO.getDestinationAccountId();
        Long sourceAccountId = transactionRequestDTO.getSourceAccountId();

        destinationAccount = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Destination account not found with ID: " + destinationAccountId));

        sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Source account not found with ID: " + sourceAccountId));

        validator.validateDeposit(amount);

        validator.validateAccountForTransactions(sourceAccount);

        if (sourceAccount instanceof CheckingAccount) {
            double fee = amount * checkingAccount.getTransactionFee();
            sourceAccount.setBalance(sourceAccount.getBalance() - amount - fee);
        } else if (sourceAccount instanceof SavingsAccount) {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        }

        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }
}
