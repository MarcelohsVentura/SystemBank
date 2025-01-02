package com.marceloventura.BankSystem.util.mapper;

import com.marceloventura.BankSystem.dto.request.ClientRequestDTO;
import com.marceloventura.BankSystem.dto.response.ClientResponseDTO;
import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Bank;
import com.marceloventura.BankSystem.entity.Client;
import com.marceloventura.BankSystem.repository.AccountRepository;
import com.marceloventura.BankSystem.repository.BankRepository;
import com.marceloventura.BankSystem.service.accountService.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ClientMapper {

    private final BankRepository bankRepository;
    private final AccountRepository accountRepository;

    // Injetando o BankRepository para buscar o banco
    public ClientMapper(BankRepository bankRepository, AccountRepository accountRepository) {
        this.bankRepository = bankRepository;
        this.accountRepository = accountRepository;
    }

    public ClientResponseDTO toClientResponseDTO (Client client) {
        return new ClientResponseDTO(client);
    }

    public List <ClientResponseDTO> toClientResponseDTO (List <Client> clientList) {
        return clientList.stream().map(ClientResponseDTO::new).collect(Collectors.toList());
    }

}
