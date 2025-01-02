package com.marceloventura.BankSystem.util.mapper;

import com.marceloventura.BankSystem.dto.request.AccountRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toAccount (AccountRequestDTO accountRequestDTO, Client client) {
        return Account.builder()
                .accountNumber(accountRequestDTO.getAccountNumber())
                .balance(accountRequestDTO.getBalance())
                .client(client)
                .accountType(accountRequestDTO.getAccountType())
                .build();
    }

    public AccountResponseDTO toAccountResponseDTO (Account account) {
        return new AccountResponseDTO(account);
    }

    public void updateAccount (Account account, AccountRequestDTO accountRequestDTO, Client client) {
        account.setAccountType(accountRequestDTO.getAccountType());
        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setBalance(accountRequestDTO.getBalance());
        account.setClient(client);
    }
}
