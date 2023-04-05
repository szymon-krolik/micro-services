package com.wazzup.eventservice.members.exception;

public class UserAlreadyJoinException extends RuntimeException {
    private static final String msg = "Already join";

    public UserAlreadyJoinException() {
        super(msg);
    }
}
