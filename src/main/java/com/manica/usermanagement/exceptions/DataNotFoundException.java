package com.manica.usermanagement.exceptions;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6253723875428L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
