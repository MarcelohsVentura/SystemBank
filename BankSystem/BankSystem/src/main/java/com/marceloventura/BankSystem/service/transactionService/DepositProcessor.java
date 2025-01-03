package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.CheckingAccount;
import com.marceloventura.BankSystem.entity.SavingsAccount;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.service.accountService.CheckingAccountService;
import com.marceloventura.BankSystem.service.accountService.SavingsAccountService;
import com.marceloventura.BankSystem.util.validator.Validator;

public class DepositProcessor implements TransactionProcessor {

    private Validator validator;

    private CheckingAccountService checkingAccountService;

    private SavingsAccountService savingsAccountService;

    private AccountRepository accountRepository;

    @Override
    public void process(Account sourceAccount, Account destinationAccount, TransactionRequestDTO transactionRequestDTO) {
        double amount = transactionRequestDTO.getAmount();

        validator.validateDeposit(amount);

        if (sourceAccount instanceof CheckingAccount) {
            checkingAccountService.checkingAccountTransactionFee(transactionRequestDTO, amount);
        } else if (sourceAccount instanceof  SavingsAccount) {
            savingsAccountService.savingsAccountMonthlyIncome(transactionRequestDTO, amount);
        }
        accountRepository.save(sourceAccount);
    }
}
