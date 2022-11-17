package com.app.banking.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BankAppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Customer not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({CustomerBadRequestException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "CustomerId / initialCredit is not present", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InitialCreditBadRequestException.class})
    public ResponseEntity<Object> handleDataIntegrity(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Initial credit has to be greater than 0", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DateBadRequestException.class})
    public ResponseEntity<Object> handleDateIntegrity(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "StartDate cannot be greater than EndDate", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
