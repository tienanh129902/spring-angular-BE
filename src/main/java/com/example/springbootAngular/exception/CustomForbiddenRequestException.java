package com.example.springbootAngular.exception;

public class CustomForbiddenRequestException extends RuntimeException {
    public CustomForbiddenRequestException(String message) {
        super(message);
    }
}
