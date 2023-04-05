package com.wazzup.eventservice.event.controller;


import com.wazzup.eventservice.event.dto.ExceptionDetailsDTO;
import com.wazzup.eventservice.event.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice("eventControllerAdvisor")
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler({EventNotFoundException.class, QrCodeNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception exception, WebRequest request) {
        ExceptionDetailsDTO exceptionDetailsDTO = new ExceptionDetailsDTO(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InternalApiException.class})
    public ResponseEntity<?> handleInternalApiException(Exception exception, WebRequest request) {
        ExceptionDetailsDTO exceptionDetailsDTO = new ExceptionDetailsDTO(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IncorrectDateException.class, InvalidEventOwnerException.class})
    public ResponseEntity<?> handleNotAcceptableException(Exception exception, WebRequest request) {
        ExceptionDetailsDTO exceptionDetailsDTO = new ExceptionDetailsDTO(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({TooManyEventsException.class})
    public ResponseEntity<?> handleTooManyRequestException(Exception exception, WebRequest request) {
        ExceptionDetailsDTO exceptionDetailsDTO = new ExceptionDetailsDTO(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetailsDTO, HttpStatus.TOO_MANY_REQUESTS);
    }

}
