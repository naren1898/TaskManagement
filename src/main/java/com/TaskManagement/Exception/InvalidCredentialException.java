package com.TaskManagement.Exception;

public class InvalidCredentialException extends Exception{
    public InvalidCredentialException() {
    }

    public InvalidCredentialException(String message) {
        super(message);
    }
}
