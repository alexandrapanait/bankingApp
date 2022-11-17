package com.app.banking.services;

import com.app.banking.dao.JpaAccountDao;
import com.app.banking.dto.AccountCreationDTO;
import com.app.banking.dto.AccountDTO;
import com.app.banking.dto.AccountTransactionDTO;
import com.app.banking.dto.CustomerTransactionsDTO;
import com.app.banking.entities.*;
import com.app.banking.exception.CustomerBadRequestException;
import com.app.banking.exception.CustomerNotFoundException;
import com.app.banking.exception.DateBadRequestException;
import com.app.banking.exception.InitialCreditBadRequestException;
import com.app.banking.repositories.AccountRepository;
import com.app.banking.repositories.AccountTransactionRepository;
import com.app.banking.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JpaAccountDao jpaAccountDao;
    @Autowired
    private AccountTransactionRepository accountTransactionRepository;
    @Transactional
    public Account createAccount(AccountCreationDTO accountCreationDTO)  {
        if(accountCreationDTO.getCustomerId() == null || accountCreationDTO.getInitialCredit() == null) {
            throw new CustomerBadRequestException();
        }
        if(accountCreationDTO.getInitialCredit() < 0 ) {
            throw new InitialCreditBadRequestException();
        }
            Customer customer = customerRepository.findCustomerByCustomerId(accountCreationDTO.getCustomerId());
            if(customer == null) {
                throw new CustomerNotFoundException();
            }
            Account account = new Account();
            account.setCustomer(customer);
            String accountNo = "";
            try {
                accountNo = jpaAccountDao.getAccountNo(customer.getCustomerId());
            } catch (NoResultException e) {
            }
            if(accountNo.isEmpty()){
                account.setAccountNo(generateAccountNo(customer));
            } else {
                account.setAccountNo(accountNo);
            }
            account.setAccountCurrency(AccountCurrency.EUR);
            account.setIBAN(generateIBAN(account.getAccountCurrency()));
            account.setAccountType("Current");
            account.setInitialCredit(accountCreationDTO.getInitialCredit());
            account.setBalance(account.getInitialCredit());

            final Date currentDate = new Date();
            account.setCreatedDate(currentDate);
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.YEAR,3);
            Date expirationDate = c.getTime();
            account.setExpirationDate(expirationDate);
            account.setActive(true);
            if(accountCreationDTO.getInitialCredit() > 0) {
                List<AccountTransaction> accountTransactionList = new ArrayList<>();
                AccountTransaction accountTransaction = new AccountTransaction();
                accountTransaction.setAccount(account);
                accountTransaction.setTransactionType(TransactionType.Deposit);
                accountTransaction.setAmount(accountCreationDTO.getInitialCredit());
                accountTransaction.setTransactionDate(currentDate);
                accountTransactionList.add(accountTransaction);
                account.setAccountTransaction(accountTransactionList);
            }
            accountRepository.save(account);
            return account;
       }

    @Transactional
    public CustomerTransactionsDTO getAllCustomerTransactions(long customerId, Long startDate, Long endDate) {
        Customer customer = customerRepository.findCustomerByCustomerId(customerId);
        if(customer == null) {
            throw new CustomerNotFoundException();
        }
        CustomerTransactionsDTO customerTransactionsDTO = new CustomerTransactionsDTO();
        customerTransactionsDTO.setCustomerName(customer.getCustomerName());
        customerTransactionsDTO.setCustomerSurname(customer.getCustomerSurname());
        List<Account> accountList = accountRepository.findAccountsByCustomerOrderByCreatedDate(customer);
        if(accountList != null) {
            List<AccountDTO> accountDTOList = new ArrayList<>();
            for(Account account : accountList) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountNo(account.getAccountNo());
                accountDTO.setIBAN(account.getIBAN());
                accountDTO.setAccountCurrency(account.getAccountCurrency());
                List<AccountTransaction> transactionList = accountTransactionRepository.findAccountTransactionsByAccountOrderByTransactionDate(account);
                if(transactionList != null) {
                    List<AccountTransactionDTO> accountTransactionDTOList = new ArrayList<>();
                    if(startDate != null && endDate != null){
                        if(startDate > endDate) {
                            throw new DateBadRequestException();
                        }
                        List<AccountTransaction> transactionListFiltered = new ArrayList<>();
                        transactionListFiltered = transactionList.stream().filter(tl ->
                                        tl.getTransactionDate().after(new Date(startDate)) && tl.getTransactionDate().before(new Date(endDate)))
                                .collect(Collectors.toList());

                        accountTransactionDTOList = createAccountTransactionDTO(transactionListFiltered);
                    }
                    else {
                        accountTransactionDTOList = createAccountTransactionDTO(transactionList);
                    }
                    accountDTO.setTransactions(accountTransactionDTOList);
                }
                accountDTO.setBalance(getBalance(transactionList));
                accountDTOList.add(accountDTO);
            }
            customerTransactionsDTO.setAccounts(accountDTOList);
        }
        return customerTransactionsDTO;
    }
    private String generateAccountNo(Customer customer) {
        Random random = new Random();
        int randomNo = random.nextInt(10000, 99999);
        String accountNo = customer.getCustomerName().substring(0,1) + customer.getCustomerSurname().substring(0,1) +
                randomNo;
        return accountNo;
    }
    private String generateIBAN(AccountCurrency currency) {
        final String countryCode = "BE";
        final String checkDigit = "11";
        final String bankCode = "BBRU";
        Random random = new Random();
        int randomNo = random.nextInt(10000000, 99999999);
        String IBAN = countryCode + checkDigit + bankCode + currency + randomNo;
        return IBAN;
    }

    private double getBalance(List<AccountTransaction> accountTransaction) {
        double balance = 0;
        if(accountTransaction != null) {
            for(AccountTransaction ac: accountTransaction) {
                balance = balance + ac.getAmount();
            }
        }
        return balance;
    }

    private List<AccountTransactionDTO> createAccountTransactionDTO(List<AccountTransaction> transactionList) {
        List<AccountTransactionDTO> accountTransactionDTOList= new ArrayList<>();
        for(AccountTransaction accountTransaction : transactionList) {
            AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
            transactionDTO.setTransactionType(accountTransaction.getTransactionType());
            transactionDTO.setAmount(accountTransaction.getAmount());
            transactionDTO.setTransactionDate(accountTransaction.getTransactionDate());
            accountTransactionDTOList.add(transactionDTO);
        }
        return accountTransactionDTOList;
    }
}
