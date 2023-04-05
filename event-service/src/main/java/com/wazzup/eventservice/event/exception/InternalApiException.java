package com.wazzup.eventservice.event.exception;

public class InternalApiException extends RuntimeException {
    private static final String msg = "Internal API error";
    public InternalApiException() {
        super(msg);
    }
}
