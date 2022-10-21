package com.fortyeight.tool.relationalvisualizer.advice.exception;

public class DataSourceInfoException extends RuntimeException {
    public DataSourceInfoException(String message) {
        super(message);
    }

    public DataSourceInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
