package com.corcino.users.application.handler;

import com.corcino.users.domain.exception.AccessDeniedException;
import com.corcino.users.domain.exception.EmailAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> errorHandler(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();

        for(FieldError error : e.getBindingResult().getFieldErrors())
            errors.add(error.getField() + " : " + error.getDefaultMessage());

        for(ObjectError error : e.getBindingResult().getGlobalErrors())
            errors.add(error.getObjectName() + " : " + error.getDefaultMessage());

        return errors;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public List<String> errorHandler(EmailAlreadyRegisteredException e) {
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return errors;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public List<String> errorHandler(AccessDeniedException e) {
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return errors;
    }

}
