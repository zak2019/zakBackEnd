package com.zak.domain.exception;

public class TeamException extends RuntimeException {

    public TeamException() {
        super("Team not found");
    }

    public TeamException(String message) {
        super(message);
    }
}
