package com.marceloventura.BankSystem.service.bankService;

import com.marceloventura.BankSystem.dto.request.BankResquestDTO;
import com.marceloventura.BankSystem.dto.response.BankResponseDTO;
import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.util.authentication.Authentication;

import java.util.List;

public interface BankService {

    BankResponseDTO findById(Long id);

    List<BankResponseDTO> findAll();

    String delete(Long id);

    BankResponseDTO createBank(BankResquestDTO bankRequestDTO);

    BankResponseDTO updateBank(Long id, BankResquestDTO bankRequestDTO);

    void addClientToBank(Long bankId, Long clientId);

    void addAccountToBank(Long bankId, Long accountId);

    Bank findBankById(Long bankId);

    void deleteClient(Long bankId, Long clientId);

    void deleteAccount (Long bankId, Long accountId);

    Authentication getAuthentication ();

}
