package com.zak.domain.exception;

public class RetroSprintCommentGroupException extends RuntimeException {

    public RetroSprintCommentGroupException() {
        super("Retro sprint comment group not found");
    }

    public RetroSprintCommentGroupException(String message) {
        super(message);
    }
}
