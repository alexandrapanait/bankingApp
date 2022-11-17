package com.app.banking.dto;

import com.app.banking.entities.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class AccountTransactionDTO {

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("transactionType")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JsonProperty("transactionDate")
    private Date transactionDate;
}
