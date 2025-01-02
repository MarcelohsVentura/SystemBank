package com.marceloventura.BankSystem.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table (name = "tb-bank")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Bank {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Setter (AccessLevel.NONE)
    private Long id;

    @Column (name = "bankName")
    private String bankName;

    @Column (name = "agencyNumber")
    private String agencyNumber;

    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    @Builder
    public Bank(String bankName, String agencyNumber, List<Client> clients, List<Account> accounts) {
        this.bankName = bankName;
        this.agencyNumber = agencyNumber;
        this.clients = clients;
        this.accounts = accounts;
    }
}
