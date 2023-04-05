package com.wazzup.eventservice.event.exception;

public class EventNotFoundException extends RuntimeException {
    private static final String msg = "Event not found";

    public EventNotFoundException() {
        super(msg);
    }
}
