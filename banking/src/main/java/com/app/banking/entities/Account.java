package com.app.banking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@GenericGenerator(
        name = "ACCOUNT_SEQ",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "ACCOUNT_SEQ"),
                @Parameter(name = "initial_value", value = "10"),
                @Parameter(name = "increment_size", value = "1")
        }
)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ")
    private long accountId;

    @Column(nullable = false)
    private String accountNo;

    @Column(nullable = false, unique = true)
    private String IBAN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonBackReference
    private Customer customer;

    @Column(nullable = false)
    private double initialCredit;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountCurrency accountCurrency;

    @Column
    private Date createdDate;

    @Column
    private Date expirationDate;

    @Column
    private boolean isActive;

    @OneToMany(targetEntity = AccountTransaction.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "account")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SUBSELECT)
    private List<AccountTransaction> accountTransaction = new ArrayList<>();

}
