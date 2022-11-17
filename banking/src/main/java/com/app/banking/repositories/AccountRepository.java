package com.app.banking.repositories;

import com.app.banking.entities.Account;
import com.app.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByCustomerOrderByCreatedDate(Customer customer);

}
