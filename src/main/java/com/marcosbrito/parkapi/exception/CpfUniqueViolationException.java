package com.marcosbrito.parkapi.exception;

public class CpfUniqueViolationException extends RuntimeException {
    public CpfUniqueViolationException(String s) {
        super(s);
    }
}
