package com.nextTasks.exception;

public class BoardExistentException extends RuntimeException {
    
    public BoardExistentException() {
        super();
    }
    
    public BoardExistentException(String message) {
        super(message);
    }
    
}
