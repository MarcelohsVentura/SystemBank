package com.marceloventura.BankSystem.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankResquestDTO {

    private String bankName;

    private String agencyNumber;
}
