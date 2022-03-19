package com.zak.domain.exception;

public class EventException extends RuntimeException {

    public EventException() {
        super("Event not found");
    }

    public EventException(String message) {
        super(message);
    }
}
