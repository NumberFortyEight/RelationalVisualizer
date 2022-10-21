package com.fortyeight.tool.relationalvisualizer.advice.exception;

public class EntryPresentationException extends RuntimeException {

    public EntryPresentationException(String message) {
        super(message);
    }

    public EntryPresentationException(String message, Throwable cause) {
        super(message, cause);
    }
}
