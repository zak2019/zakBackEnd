package com.zak.domain.exception;

public class RetroSprintCommentException extends RuntimeException {

    public RetroSprintCommentException() {
        super("Retro sprint comment not found");
    }

    public RetroSprintCommentException(String message) {
        super(message);
    }
}
