package com.zak.domain.exception;

public class NotificationTypeException extends RuntimeException {

    public NotificationTypeException() {
        super("Notification type not found");
    }

    public NotificationTypeException(String message) {
        super(message);
    }
}
