package com.jazva.challenge.exeption;

import com.jazva.challenge.exeption.ex.InvalidArgumentRestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * exceptions in the Catches infrastructure...
 *
 * @author HARUT
 */
@ControllerAdvice
public class InvalidArgumentRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INVALID_QUANTITY_MESSAGE = "The quantity of product in specified location is - %s " +
            "and not enough to reduce";

    @ExceptionHandler(value  = { InvalidArgumentRestException.class })
    protected ResponseEntity<Object> handleIvalidArgument(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, String.format(INVALID_QUANTITY_MESSAGE, ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
