package com.hobbygo.api.hobbygoapi.restapi.advice;

import com.hobbygo.api.hobbygoapi.restapi.exception.*;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice("com.hobbygo.api.hobbygoapi.restapi.controller")
public class ValidationErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors.VndError userNotFoundExceptionHandler(UserNotFoundException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

/*
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors userNotFoundExceptionHandler(MethodArgumentNotValidException ex) {
        return new VndErrors("validation_error", errorMessage(ex.getBindingResult().getFieldError()));
    }
*/

    @ResponseBody
    @ExceptionHandler(DateParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors.VndError dateParseExceptionHandler(DateParseException ex) {
        return new VndErrors.VndError("error", errorMessage(ex.getField(), ex.getValue()));
    }

    @ResponseBody
    @ExceptionHandler(UserNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    VndErrors.VndError userNameAlreadyExistExceptionHandler(UserNameAlreadyExistException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    VndErrors.VndError emailAlreadyExistExceptionHandler(EmailAlreadyExistException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SendConfirmationEmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    VndErrors.VndError sendConfirmationEmailExceptionHandler(SendConfirmationEmailException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

    private String errorMessage(FieldError fieldError) {
        return errorMessage(fieldError.getField(), fieldError.getRejectedValue());
    }

    private String errorMessage(String field, Object value) {

        return String.format("Field [%s] validation failed : rejected value [%s]",
                field, value);

    }

}
