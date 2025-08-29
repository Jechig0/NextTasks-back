package com.nextTasks.exception;

public class TaskExistException extends RuntimeException {
    public TaskExistException(String message) {
        super(message);
    }
    public TaskExistException() {
        super();
    }
}
