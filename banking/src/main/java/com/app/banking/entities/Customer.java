package com.app.banking.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerSurname;

    @OneToMany(targetEntity = Account.class, fetch = FetchType.LAZY, mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Account> account = new ArrayList<>();
}
