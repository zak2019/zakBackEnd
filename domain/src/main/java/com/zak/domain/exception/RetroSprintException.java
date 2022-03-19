package com.zak.domain.exception;

public class RetroSprintException extends RuntimeException {

    public RetroSprintException() {
        super("Retro sprint not found");
    }

    public RetroSprintException(String message) {
        super(message);
    }
}
