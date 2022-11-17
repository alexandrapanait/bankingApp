package com.app.banking.controller;

import com.app.banking.dto.AccountCreationDTO;
import com.app.banking.dto.CustomerTransactionsDTO;
import com.app.banking.entities.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class CustomerControllerTest {

    private final CustomerController customerController;

    private final CustomerTransactionsDTO customerTransactionsDTO = new CustomerTransactionsDTO();

    private final Account account = new Account();

    private AccountCreationDTO accountCreationDTO = new AccountCreationDTO();

    private AccountCreationDTO createAccountCreationDTOData(AccountCreationDTO accountCreationDTO) {
        accountCreationDTO.setCustomerId(1L);
        accountCreationDTO.setInitialCredit(1000.0);
        return accountCreationDTO;
    }

    @Autowired
    public  CustomerControllerTest(CustomerController customerController) {
        this.customerController = customerController;
    }

    @Test
    @DisplayName("Test create account")
    void createAccount() {
        ResponseEntity<Account> result = customerController.createAccount(createAccountCreationDTOData(accountCreationDTO));
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test get customer info")
    void getAllCustomerTransactions_OK() {
        ResponseEntity<CustomerTransactionsDTO> result = customerController.getCustomerTransactions(1, null, null);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

//    @Test
//    @DisplayName("Test get customer info")
//    void getCustomerTransactionsByDate_OK() {
//        ResponseEntity<CustomerTransactionsDTO> result = customerController.getCustomerTransactions(1, ()1668124800000, null);
//        assertEquals(result.getStatusCode(), HttpStatus.OK);
//    }

//    @Test
//    @DisplayName("Test customer not found")
//    void getCustomerTransactions_NOTOK() {
//        ResponseEntity<CustomerTransactionsDTO> result = customerController.getCustomerTransactions(4, null, null);
//        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
//    }
}