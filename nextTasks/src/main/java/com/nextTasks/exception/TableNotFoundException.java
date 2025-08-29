package com.nextTasks.exception;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super(message);
    }
    public TableNotFoundException() {
        super();
    }
}
