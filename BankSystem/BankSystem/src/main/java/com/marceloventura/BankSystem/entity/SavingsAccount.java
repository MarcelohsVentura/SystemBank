package com.marceloventura.BankSystem.entity;

import com.marceloventura.BankSystem.enumeration.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SavingsAccount extends Account{

    private double monthlyIncome = 0.02;

    public SavingsAccount(String accountNumber, double balance, Client client, double monthlyIncome, AccountType accountType) {
        super(accountNumber, balance, client, accountType);
        this.monthlyIncome = monthlyIncome;
    }

    public SavingsAccount(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

}
