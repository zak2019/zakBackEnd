package com.zak.domain.exception;

public class EventCommentException extends RuntimeException {

    public EventCommentException() {
        super("Event comment not found");
    }

    public EventCommentException(String message) {
        super(message);
    }
}
