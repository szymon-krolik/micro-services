package com.wazzup.eventservice.event.exception;

public class InvalidEventOwnerException extends RuntimeException {
    private static final String msg = "Invalid event owner";

    public InvalidEventOwnerException() {
        super(msg);
    }
}
