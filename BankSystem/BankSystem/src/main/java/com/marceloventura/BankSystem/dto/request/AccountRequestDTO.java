package com.marceloventura.BankSystem.dto.request;

import com.marceloventura.BankSystem.enumeration.AccountType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRequestDTO {

    private double balance;

    private AccountType accountType;

    private String accountNumber;

    private Long clientId;

    private Long bankId;
}

