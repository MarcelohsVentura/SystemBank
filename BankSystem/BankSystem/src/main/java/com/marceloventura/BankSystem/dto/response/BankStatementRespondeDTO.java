package com.marceloventura.BankSystem.dto.response;

import com.marceloventura.BankSystem.entity.BankStatement;
import com.marceloventura.BankSystem.entity.Transaction;
import lombok.Getter;

@Getter
public class BankStatementRespondeDTO {

    private Long id;

    private Transaction transaction;

    public BankStatementRespondeDTO(BankStatement bankStatement) {
        this.id = bankStatement.getId();
        this.transaction = bankStatement.getTransaction();
    }
}
