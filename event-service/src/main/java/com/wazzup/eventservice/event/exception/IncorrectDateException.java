package com.wazzup.eventservice.event.exception;

public class IncorrectDateException extends RuntimeException {
    private static final String msg = "Incorrect date exception";

    public IncorrectDateException() {
        super(msg);
    }
}
