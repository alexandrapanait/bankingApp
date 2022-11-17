package com.app.banking.dto;

import com.app.banking.entities.AccountCurrency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class AccountDTO {

    @JsonProperty(value = "accountNo")
    private String accountNo;

    @JsonProperty(value = "IBAN")
    private String IBAN;

    @JsonProperty(value = "balance")
    private double balance;

    @JsonProperty(value = "currency")
    @Enumerated(EnumType.STRING)
    private AccountCurrency accountCurrency;

    @JsonProperty(value = "transactions")
    private List<AccountTransactionDTO> transactions;

}
