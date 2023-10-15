package com.example.springbootAngular.exceptionHandler;

public class CustomForbiddenRequestException extends RuntimeException {
    public CustomForbiddenRequestException(String message) {
        super(message);
    }
}
