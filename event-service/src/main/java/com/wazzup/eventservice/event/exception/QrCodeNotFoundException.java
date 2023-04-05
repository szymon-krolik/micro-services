package com.wazzup.eventservice.event.exception;

public class QrCodeNotFoundException extends RuntimeException {
    private static final String msg = "Qr code not found";

    public QrCodeNotFoundException() {
        super(msg);
    }
}
