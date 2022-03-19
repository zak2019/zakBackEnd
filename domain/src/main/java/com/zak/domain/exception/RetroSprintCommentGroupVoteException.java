package com.zak.domain.exception;

public class RetroSprintCommentGroupVoteException extends RuntimeException {

    public RetroSprintCommentGroupVoteException() {
        super("Retro sprint comment group vote not found");
    }

    public RetroSprintCommentGroupVoteException(String message) {
        super(message);
    }
}
