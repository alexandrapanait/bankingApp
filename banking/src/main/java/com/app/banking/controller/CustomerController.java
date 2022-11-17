package com.app.banking.controller;

import com.app.banking.dto.AccountCreationDTO;
import com.app.banking.dto.CustomerTransactionsDTO;
import com.app.banking.entities.Account;
import com.app.banking.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Account> createAccount(@RequestBody AccountCreationDTO accountCreationDTO) {
        log.info("Account creation has started");
        Account customerAccount = accountService.createAccount(accountCreationDTO);
        return new ResponseEntity<>(
                customerAccount, HttpStatus.OK
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerTransactionsDTO> getCustomerTransactions(@PathVariable long customerId,
                                                                           @RequestParam(required = false) Long startDate,
                                                                           @RequestParam(required = false) Long endDate) {
        log.info("Retrieve customer information");
        CustomerTransactionsDTO customerTransactionsDTO = accountService.getAllCustomerTransactions(customerId, startDate, endDate);
        return new ResponseEntity<>(
                customerTransactionsDTO, HttpStatus.OK
        );
    }
}
