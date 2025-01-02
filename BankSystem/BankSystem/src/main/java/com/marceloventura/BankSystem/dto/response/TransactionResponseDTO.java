package com.marceloventura.BankSystem.dto.response;

import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Transaction;
import com.marceloventura.BankSystem.enumeration.AccountType;
import com.marceloventura.BankSystem.enumeration.TransactionType;

import java.util.Date;

public class TransactionResponseDTO {

    private Long id;

    private double transactionValue;

    private Date date;

    private Account sourceAccount;

    private Account destinationAccount;

    private double previousBalance;

    private double laterBalance;

    private double amount;

    private TransactionType transactionType;

    private AccountType accountType;

    public TransactionResponseDTO (Transaction transaction) {
        this.id = transaction.getId();
        this.transactionValue = transaction.getTransactionValue();
        this.date = transaction.getDate();
        this.sourceAccount = transaction.getSourceAccount();
        this.destinationAccount = transaction.getDestinationAccount();
        this.previousBalance = transaction.getPreviousBalance();
        this.laterBalance = transaction.getLaterBalance();
        this.amount = transaction.getAmount();
        this.transactionType = transaction.getTransactionType();
        this.accountType = transaction.getAccountType();
    }

}
