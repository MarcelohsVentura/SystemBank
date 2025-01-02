package com.marceloventura.BankSystem.dto.request;

import com.marceloventura.BankSystem.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ClientRequestDTO {

    private String name;

    private String idNumber;

    private String password;

    private Long bankId;
}
