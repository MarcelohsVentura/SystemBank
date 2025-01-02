package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.dto.response.TransactionResponseDTO;

public interface TransactionService {

    TransactionResponseDTO findById(Long id);

    public void processTransaction (TransactionRequestDTO transactionRequestDTO);
}
