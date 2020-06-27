package com.dgusev.ab.task1.rest;

import com.dgusev.ab.task1.exception.AtmNotFoundException;
import com.dgusev.ab.task1.rest.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {


    @ExceptionHandler(AtmNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(AtmNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(), HttpStatus.NOT_FOUND);
    }
}
