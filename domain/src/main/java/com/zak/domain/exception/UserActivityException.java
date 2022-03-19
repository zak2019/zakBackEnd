package com.zak.domain.exception;

public class UserActivityException extends RuntimeException {

    public UserActivityException() {
        super("User activity not found");
    }

    public UserActivityException(String message) {
        super(message);
    }
}
