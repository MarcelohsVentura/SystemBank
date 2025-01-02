package com.marceloventura.BankSystem.dto.request;

import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.enumeration.AccountType;
import com.marceloventura.BankSystem.enumeration.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TransactionRequestDTO {

    private double transactionValue;

    private Account sourceAccount;

    private Account destinationAccount;

    private double previousBalance;

    private double amount;

    private TransactionType transactionType;

    private AccountType accountType;
}
