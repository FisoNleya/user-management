package com.manica.usermanagement.exceptions;

public class DataHandlingException extends RuntimeException {

    private static final long serialVersionUID = 64773847895340907L;

    public DataHandlingException(String message) {
        super(message);
    }
}
