package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;
import com.marceloventura.BankSystem.entity.Account;

public interface TransactionProcessor {
    void process (Account sourceAccount, Account destinationAccount, TransactionRequestDTO transactionRequestDTO);
}
