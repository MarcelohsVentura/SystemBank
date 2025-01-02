package com.marceloventura.BankSystem.enumeration;

public enum TransactionType {
    DEPOSIT ("Deposit"),
    WITHDRAWAL ("Withdrawal"),
    TRANSFER ("Transfer");

    private final String transactionType;

    TransactionType (String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType () {
        return  transactionType;
    }

}
