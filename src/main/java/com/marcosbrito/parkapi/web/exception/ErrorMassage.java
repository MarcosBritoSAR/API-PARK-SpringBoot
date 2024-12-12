package com.marcosbrito.parkapi.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorMassage {
    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    private Map<String, String> errors;

    public ErrorMassage(HttpServletRequest request, HttpStatus httpStatus, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.message = message;
    }

    public ErrorMassage(HttpServletRequest request, HttpStatus httpStatus, String message, BindingResult bindingResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.message = message;

        addErros(bindingResult);
    }

    private void addErros(BindingResult bindingResult) {
        //Precisamos devolver como campo de validacoes, um objeto JSON. Vamos usar um map usando a chave como o campo e o value como a mesagem de erro
        errors = new HashMap<>();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
