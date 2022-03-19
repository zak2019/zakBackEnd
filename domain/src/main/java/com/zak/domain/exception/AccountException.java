package com.zak.domain.exception;

public class AccountException extends RuntimeException {

    public AccountException() {
        super("Account not found");
    }

    public AccountException(String message) {
        super(message);
    }
}
