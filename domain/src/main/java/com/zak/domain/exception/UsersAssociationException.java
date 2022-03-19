package com.zak.domain.exception;

public class UsersAssociationException extends RuntimeException {

    public UsersAssociationException() {
        super("Users association not found");
    }

    public UsersAssociationException(String message) {
        super(message);
    }
}
