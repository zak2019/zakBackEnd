package com.zak.domain.exception;

public class EventTypeException extends RuntimeException {

    public EventTypeException() {
        super("Event type not found");
    }

    public EventTypeException(String message) {
        super(message);
    }
}
