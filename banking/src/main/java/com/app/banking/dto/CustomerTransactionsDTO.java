package com.app.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerTransactionsDTO {

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("customerSurname")
    private String customerSurname;

    @JsonProperty
    private List<AccountDTO> accounts;

}
