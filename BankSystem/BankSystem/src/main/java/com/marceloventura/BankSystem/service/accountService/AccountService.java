package com.marceloventura.BankSystem.service.accountService;

import com.marceloventura.BankSystem.dto.request.AccountRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.entity.CheckingAccount;
import com.marceloventura.BankSystem.entity.SavingsAccount;
import com.marceloventura.BankSystem.util.authentication.Authentication;

public interface AccountService {

    AccountResponseDTO findById(Long id);

    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);

    AccountResponseDTO update(Long id, AccountRequestDTO accountRequestDTO, String password);

    String deleteAccount(Long id, String password);

}
