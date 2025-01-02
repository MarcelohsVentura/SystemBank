package com.marceloventura.BankSystem.util.mapper;

import com.marceloventura.BankSystem.dto.response.TransactionResponseDTO;
import com.marceloventura.BankSystem.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDTO toTransactionResponseDTO (Transaction transaction) {
        return new TransactionResponseDTO(transaction);
    }


}
