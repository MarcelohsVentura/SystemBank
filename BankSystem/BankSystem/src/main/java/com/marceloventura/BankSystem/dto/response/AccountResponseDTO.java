package com.marceloventura.BankSystem.dto.response;

import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.enumeration.AccountType;
import lombok.Getter;

@Getter
public class AccountResponseDTO {

    private Long id;

    private String accountNumber;

    private double balance;

    private Long clientId;

    private AccountType accountType;

    private Bank bank;

    public AccountResponseDTO (Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.clientId = account.getClient().getId();
        this.accountType = account.getAccountType();
        this.bank = account.getBank();
    }


}
