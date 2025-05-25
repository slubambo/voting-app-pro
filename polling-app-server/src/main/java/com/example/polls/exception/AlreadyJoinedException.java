package com.example.polls.exception;

public class AlreadyJoinedException extends RuntimeException {
    public AlreadyJoinedException(String message) {
        super(message);
    }
}
