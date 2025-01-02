package com.marceloventura.BankSystem.util.mapper;

import com.marceloventura.BankSystem.dto.response.BankResponseDTO;
import com.marceloventura.BankSystem.dto.response.BankStatementRespondeDTO;
import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.entity.BankStatement;
import org.springframework.stereotype.Component;

@Component
public class BankStatementMapper {

    public BankStatementRespondeDTO toBankStatementRespondeDTO (BankStatement bankStatement) {
        return new BankStatementRespondeDTO(bankStatement);
    }
}
