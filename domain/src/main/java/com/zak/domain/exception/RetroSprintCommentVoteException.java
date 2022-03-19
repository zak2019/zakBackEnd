package com.zak.domain.exception;

public class RetroSprintCommentVoteException extends RuntimeException {

    public RetroSprintCommentVoteException() {
        super("Retro sprint comment vote not found");
    }

    public RetroSprintCommentVoteException(String message) {
        super(message);
    }
}
