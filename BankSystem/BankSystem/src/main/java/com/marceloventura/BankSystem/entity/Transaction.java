package com.marceloventura.BankSystem.entity;

import com.marceloventura.BankSystem.enumeration.AccountType;
import com.marceloventura.BankSystem.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity // entidades anotadas com @Entity não são gerenciadas como beans pelo Spring
@Table (name = "tb-transaction")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue
    @Column (name = "transactionId", nullable = false, unique = true)
    @Setter (AccessLevel.NONE)
    private Long id;

    @Column (name = "transactionValue", nullable = false)
    private double transactionValue;

    @Column (name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn (name = "sourceAccount_id", nullable = false)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn (name = "destinationAccount_id", nullable = false)
    private Account destinationAccount;

    @Column (nullable = false)
    private double previousBalance;

    @Column (nullable = false)
    private double laterBalance;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private TransactionType transactionType;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private AccountType accountType;

    @Column (nullable = false)
    private double amount;

    @Builder
    public Transaction(double transactionValue, Date date, Account sourceAccount, Account destinationAccount, double previousBalance, double laterBalance, double amount, TransactionType transactionType, AccountType accountType) {
        this.transactionValue = transactionValue;
        this.date = date;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.previousBalance = previousBalance;
        this.laterBalance = laterBalance;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.amount = amount;
    }
}
