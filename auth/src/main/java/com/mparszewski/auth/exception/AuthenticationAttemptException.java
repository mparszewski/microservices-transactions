package com.mparszewski.auth.exception;

public class AuthenticationAttemptException extends RuntimeException {

    public AuthenticationAttemptException(String message) {
        super(message);
    }
}
