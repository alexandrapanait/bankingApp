package com.app.banking.dao;

import com.app.banking.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class JpaAccountDao implements JpaDao<Account> {

    private EntityManager entityManager;

    @Autowired
    public JpaAccountDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public String getAccountNo(long customerId) {
        String accountNo = (String) entityManager.createQuery("select a.accountNo from Account a" +
                " inner join a.customer c" +
                " where c.customerId = :customerId")
                .setParameter("customerId", customerId)
                .getSingleResult();
        return accountNo;
    }
}
