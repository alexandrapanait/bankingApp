package com.app.banking.repositories;

import com.app.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByCustomerId(Long customerId);
}
