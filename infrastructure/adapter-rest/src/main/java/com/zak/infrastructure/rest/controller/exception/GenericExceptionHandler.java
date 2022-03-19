package com.zak.infrastructure.rest.controller.exception;

import com.zak.domain.exception.*;
import com.zak.domain.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.AccessControlException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({UserNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleException(final UserNotFoundException e) {
        return handleDefaultException(e, HttpStatus.NOT_FOUND, "User exception: " + e.getMessage());
    }

    @ExceptionHandler({AccountException.class})
    protected ResponseEntity<ErrorResponse> handleException(final AccountException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, "Account exception: " + e.getMessage());
    }

    @ExceptionHandler({UsersAssociationException.class})
    protected ResponseEntity<ErrorResponse> handleException(final UsersAssociationException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, "Users association exception: " + e.getMessage());
    }

    @ExceptionHandler({TeamException.class})
    protected ResponseEntity<ErrorResponse> handleException(final TeamException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, "Team exception: " + e.getMessage());
    }

    @ExceptionHandler({EventException.class})
    protected ResponseEntity<ErrorResponse> handleException(final EventException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, "Event exception: " + e.getMessage());
    }

    @ExceptionHandler({EventTypeException.class})
    protected ResponseEntity<ErrorResponse> handleException(final EventTypeException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, "Event type exception: " + e.getMessage());
    }

    @ExceptionHandler({EventCommentException.class})
    protected ResponseEntity<ErrorResponse> handleException(final EventCommentException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({RetroSprintCommentException.class})
    protected ResponseEntity<ErrorResponse> handleException(final RetroSprintCommentException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({RetroSprintCommentGroupException.class})
    protected ResponseEntity<ErrorResponse> handleException(final RetroSprintCommentGroupException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({RetroSprintCommentGroupVoteException.class})
    protected ResponseEntity<ErrorResponse> handleException(final RetroSprintCommentGroupVoteException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({RetroSprintCommentVoteException.class})
    protected ResponseEntity<ErrorResponse> handleException(final RetroSprintCommentVoteException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({RetroSprintException.class})
    protected ResponseEntity<ErrorResponse> handleException(final RetroSprintException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({TaskException.class})
    protected ResponseEntity<ErrorResponse> handleException(final TaskException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({UserActivityException.class})
    protected ResponseEntity<ErrorResponse> handleException(final UserActivityException e) {
        return handleDefaultException(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({NotificationException.class})
    protected ResponseEntity<ErrorResponse> handleException(final NotificationException e) {
        return handleDefaultException(e, HttpStatus.NOT_FOUND, "Notification exception: " + e.getMessage());
    }

    @ExceptionHandler({NotificationTypeException.class})
    protected ResponseEntity<ErrorResponse> handleException(final NotificationTypeException e) {
        return handleDefaultException(e, HttpStatus.NOT_FOUND, "Notification type exception: " + e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, AccessControlException.class})
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        HttpStatus status = null;
        String prefix = "";
        if (e instanceof IllegalArgumentException || e instanceof AccessControlException) {
            status = HttpStatus.BAD_REQUEST;
            prefix = "Bad request due to user error: ";
        }

        ErrorResponse response = ErrorResponse
                .builder()
                .code(Optional.ofNullable(status).toString())
                .message(prefix + e.getMessage())
                .local(LocalDateTime.now())
                .build();


        return ResponseEntity
                .status(status)
                .body(response);

    }

    private ResponseEntity<ErrorResponse> handleDefaultException(Exception e, HttpStatus httpStatus) {
        return handleDefaultException(e, httpStatus, e.getMessage());
    }


    private ResponseEntity<ErrorResponse> handleDefaultException(Exception e, HttpStatus httpStatus, String message) {
        log.error(e.getMessage(), e);

        ErrorResponse error = ErrorResponse
                .builder()
                .code(httpStatus.toString())
                .message(message)
                .local(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(httpStatus)
                .body(error);
    }
}
