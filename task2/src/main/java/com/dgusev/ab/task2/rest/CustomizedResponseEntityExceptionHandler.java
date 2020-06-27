package com.dgusev.ab.task2.rest;

import com.dgusev.ab.task2.exception.UserNotFoundException;
import com.dgusev.ab.task2.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(), HttpStatus.NOT_FOUND);
    }
}
