package com.marceloventura.BankSystem.enumeration;

public enum AccountType {
    SAVINGS_ACCOUNT ("Savings account"),
    CHECKING_ACCOUNT ("Checking account");

    private final String accountType;

    AccountType (String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType () {
        return accountType;
    }
}
