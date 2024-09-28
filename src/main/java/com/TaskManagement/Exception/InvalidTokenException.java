package com.TaskManagement.Exception;

public class InvalidTokenException extends Exception{

    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
