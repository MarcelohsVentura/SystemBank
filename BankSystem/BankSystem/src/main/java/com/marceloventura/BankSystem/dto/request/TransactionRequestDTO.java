package com.marceloventura.BankSystem.dto.request;

import com.marceloventura.BankSystem.enumeration.AccountType;
import com.marceloventura.BankSystem.enumeration.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequestDTO {

    private Long sourceAccountId;

    private Long destinationAccountId;

    private double amount;

    private TransactionType transactionType;

    private AccountType accountType;
}
