package com.gizasystems.deliveryservice.controller.advice;

import com.gizasystems.deliveryservice.exception.DeliveryPersonNotFoundException;
import com.gizasystems.deliveryservice.exception.DeliveryPersonAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DeliveryPersonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeliveryPersonNotFoundException.class)
    public final ResponseEntity<Object> handleDeliveryPersonNotFoundException(
        DeliveryPersonNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeliveryPersonAlreadyExistsException.class)
    public final ResponseEntity<Object> handleDeliveryPersonAlreadyExistsException(
        DeliveryPersonAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
