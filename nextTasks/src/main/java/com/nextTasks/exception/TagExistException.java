package com.nextTasks.exception;

public class TagExistException extends RuntimeException {
    public TagExistException(String message) {
        super(message);
    }
    public TagExistException() {
        super("Tag already exists");
    }
}
