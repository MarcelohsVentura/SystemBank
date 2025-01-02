package com.marceloventura.BankSystem.util.mapper;


import com.marceloventura.BankSystem.dto.request.BankResquestDTO;
import com.marceloventura.BankSystem.dto.response.BankResponseDTO;
import com.marceloventura.BankSystem.entity.Bank;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class BankMapper {

    private final ClientMapper clientMapper;
    private final AccountMapper accountMapper;

    public BankMapper(ClientMapper clientMapper, AccountMapper accountMapper) {
        this.clientMapper = clientMapper;
        this.accountMapper = accountMapper;
    }

    public BankResponseDTO toBankResponseDTO (Bank bank) {
        return new BankResponseDTO(bank);
    }

    public List<BankResponseDTO> toBankResponseDTO (List <Bank> bankList) {
        return bankList.stream().map(BankResponseDTO::new).collect(Collectors.toList());
    }

}
