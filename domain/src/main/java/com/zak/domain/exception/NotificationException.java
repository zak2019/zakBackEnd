package com.zak.domain.exception;

public class NotificationException extends RuntimeException {

    public NotificationException() {
        super("Notification not found");
    }

    public NotificationException(String message) {
        super(message);
    }
}
