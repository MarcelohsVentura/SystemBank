package com.marceloventura.BankSystem.service.transactionService;

import com.marceloventura.BankSystem.enumeration.TransactionType;
import com.marceloventura.BankSystem.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class TransactionProcessorFactory {

    private final Map <TransactionType, TransactionProcessor> processorMap = new HashMap<>();

    public TransactionProcessorFactory (AccountRepository accountRepository) {
        processorMap.put(TransactionType.DEPOSIT, new DepositProcessor());
        processorMap.put(TransactionType.WITHDRAWAL, new WithdrawProcessor());
        processorMap.put(TransactionType.TRANSFER, new TransferProcessor(accountRepository));
    }

    public TransactionProcessor getProcessor (TransactionType transactionType) {
        TransactionProcessor processor = processorMap.get(transactionType);

        if (processor == null) {
            throw new IllegalArgumentException("Unsupported transaction type");
        }
        return processor;
    }
}
