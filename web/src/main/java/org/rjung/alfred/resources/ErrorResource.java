package org.rjung.alfred.resources;

import javax.validation.ConstraintViolationException;

import org.rjung.alfred.objects.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorResource {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Errors constraintViolationException(ConstraintViolationException ex) {
        return new Errors(ex);
    }
}
