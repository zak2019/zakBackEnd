package com.zak.domain.exception;

public class TaskException extends RuntimeException {

    public TaskException() {
        super("Task not found");
    }

    public TaskException(String message) {
        super(message);
    }
}
