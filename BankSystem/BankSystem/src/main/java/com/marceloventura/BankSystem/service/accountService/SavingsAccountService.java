package com.marceloventura.BankSystem.service.accountService;

import com.marceloventura.BankSystem.dto.request.TransactionRequestDTO;

public interface SavingsAccountService {

    double savingsAccountMonthlyIncome (TransactionRequestDTO transactionRequestDTO, double amount);
}
