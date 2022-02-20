package com.maveric.account.demo.exception;

public class AccountTypeExistsException extends RuntimeException{
    public AccountTypeExistsException(String message) {
        super(message);
    }
}
