package com.app.banking.dao;

import com.app.banking.entities.AccountTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Component
public class JpaTransactionDao implements JpaDao<AccountTransaction>{

    private EntityManager entityManager;

    @Autowired
    public JpaTransactionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<AccountTransaction> findTransactionsByAccountAndDate(long accountId, Date startDate, Date endDate) {
        List<AccountTransaction> transactionList = entityManager.createQuery("select t from AccountTransaction t " +
                "inner join t.account a " +
                "where a.accountId = :accountId " +
                "and t.transactionDate >= :startDate " +
                "and t.transactionDate <= :endDate " +
                "order by t.transactionDate")
                .setParameter("accountId", accountId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

        return transactionList;
    }
}
