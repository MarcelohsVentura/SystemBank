package com.marceloventura.BankSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "tb-statement")
@NoArgsConstructor
@Getter
@Setter
public class BankStatement {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "statementId", nullable = false, unique = true)
    @Setter (AccessLevel.NONE)
    private Long id;

    @ManyToOne
    private Transaction transaction;
    // Ao invés de usar o account, uso o transaction para ter:
    // Rastreamento individual de cada transação
    // Relatórios financeiros claros e auditáveis
    // Consultas flexíveis no histórico transacional

    @Column (nullable = false)
    private String detailedDescription;

    @Builder
    public BankStatement(Transaction transaction, String detailedDescription) {
        this.transaction = transaction;
        this.detailedDescription = detailedDescription;
    }
}
