package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.CheckingAccount;
import com.marceloventura.BankSystem.entity.SavingsAccount;
import com.marceloventura.BankSystem.repository.AccountRepository;

import com.marceloventura.BankSystem.util.validator.Validator;

public class WithdrawProcessor  implements TransactionProcessor {

    private Validator validator;

    private AccountRepository accountRepository;

    private CheckingAccount checkingAccount;

    @Override
    public void process(Account sourceAccount, Account destinationAccount, TransactionRequestDTO transactionRequestDTO) {
        double amount = transactionRequestDTO.getAmount();

        validator.validateDeposit(amount);

        validator.validateAccountForTransactions(sourceAccount);

        if (sourceAccount instanceof CheckingAccount) {
            double fee = amount * checkingAccount.getTransactionFee();
            sourceAccount.setBalance(sourceAccount.getBalance() - amount - fee);
        } else if (sourceAccount instanceof SavingsAccount) {
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        }

        accountRepository.save(sourceAccount);
    }
}
