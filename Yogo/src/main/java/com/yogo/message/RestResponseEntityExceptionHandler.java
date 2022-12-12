package com.yogo.message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<?> handleInternal(HttpClientErrorException ex) {
        return ResponseMessage.build(ex.getStatusCode(), ex.getStatusText(), null);
    }
}
