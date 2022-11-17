package com.app.banking.repositories;

import com.app.banking.entities.Account;
import com.app.banking.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
    List<AccountTransaction> findAccountTransactionsByAccountOrderByTransactionDate(Account account);
}
