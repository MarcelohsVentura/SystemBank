package com.marceloventura.BankSystem.entity;

import com.marceloventura.BankSystem.enumeration.AccountType;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class CheckingAccount extends Account{

    private double transactionFee = 0.05;

    public CheckingAccount(double balance, Client client, double transactionFee, AccountType accountType, String accountNumber) {
        super(accountNumber, balance, client, accountType);
        this.transactionFee = transactionFee;
    }

    public CheckingAccount(double transactionFee) {
        this.transactionFee = transactionFee;
    }
}
