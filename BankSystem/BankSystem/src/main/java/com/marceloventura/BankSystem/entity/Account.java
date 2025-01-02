package com.marceloventura.BankSystem.entity;


import com.marceloventura.BankSystem.enumeration.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "tb-account")
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId", nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "accountNumber", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false, unique = true)
    private Client client;

    @Enumerated(EnumType.STRING)  // Garante que o nome do enum seja salvo no banco de dados
    @Column(name = "accountType", nullable = false)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "bankId", nullable = false)
    private Bank bank;

    @Builder
    public Account(String accountNumber, double balance, Client client, AccountType accountType, Bank bank) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.client = client;
        this.accountType = accountType;
        this.bank = bank;
    }

    public Account(String accountNumber, double balance, Client client, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.client = client;
        this.accountType = accountType;
    }
}
