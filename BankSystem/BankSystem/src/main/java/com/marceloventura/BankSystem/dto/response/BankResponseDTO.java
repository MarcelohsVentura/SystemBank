package com.marceloventura.BankSystem.dto.response;


import com.marceloventura.BankSystem.entity.Bank;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BankResponseDTO {

    private Long id;

    private String bankName;

    private String agencyNumber;

    private List<ClientResponseDTO> clients;

    private List <AccountResponseDTO> accounts;

    public BankResponseDTO(Bank bank) {
        this.id = bank.getId();
        this.bankName = bank.getBankName();
        this.agencyNumber = bank.getAgencyNumber();
        this.clients = bank.getClients().stream().map(ClientResponseDTO::new).collect(Collectors.toList());
        this.accounts = bank.getAccounts().stream().map(AccountResponseDTO::new).collect(Collectors.toList());
    }

}
