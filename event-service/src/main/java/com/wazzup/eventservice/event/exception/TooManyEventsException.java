package com.wazzup.eventservice.event.exception;

public class TooManyEventsException extends RuntimeException {
    private static final String msg = "Too many events";

    public TooManyEventsException() {
        super(msg);
    }
}
