package com.marceloventura.BankSystem.service.accountService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;

public interface CheckingAccountService {

    double checkingAccountTransactionFee (TransactionRequestDTO transactionRequestDTO, double amount);
}
