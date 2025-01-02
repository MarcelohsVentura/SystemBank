package com.marceloventura.BankSystem.dto.response;

import com.marceloventura.BankSystem.entity.Account;
import com.marceloventura.BankSystem.entity.Client;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ClientResponseDTO {

    private Long id;

    private String name;

    private String idNumber;

    private List<Long> accountIds;

    public ClientResponseDTO (Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.idNumber = client.getIdNumber();
        // Convertendo a lista de contas para apenas os IDs
        this.accountIds = client.getAccounts().stream()
                .map(Account::getId) // Extrai o ID de cada conta
                .collect(Collectors.toList()); // Coleta em uma lista
    }


}
