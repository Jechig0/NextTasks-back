package com.nextTasks.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(String message) {
        super(message);
    }
    public TagNotFoundException() {
        super("Tag not found");
    }
}
