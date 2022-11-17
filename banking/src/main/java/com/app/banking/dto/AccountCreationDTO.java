package com.app.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCreationDTO {

    @JsonProperty(value = "customerId", required = true)
    private Long customerId;

    @JsonProperty(value = "initialCredit", required = true)
    private Double initialCredit;

}
