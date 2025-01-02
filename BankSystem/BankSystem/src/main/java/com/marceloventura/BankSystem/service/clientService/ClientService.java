package com.marceloventura.BankSystem.service.clientService;

import com.marceloventura.BankSystem.dto.request.ClientRequestDTO;
import com.marceloventura.BankSystem.dto.response.AccountResponseDTO;
import com.marceloventura.BankSystem.dto.response.ClientResponseDTO;
import com.marceloventura.BankSystem.entity.Client;

import java.util.List;

public interface ClientService {

    ClientResponseDTO findById (Long id);

    List <ClientResponseDTO> findAll ();

    Client createClient(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO update (Long id, ClientRequestDTO clientRequestDTO);

    String delete (Long id);

    List <AccountResponseDTO> getAccountsByClientId (Long clientId);

    void addAccountToClient(Long bankId, Long accountId, Long clientId);

    Client findClientById(Long clientId);
}
