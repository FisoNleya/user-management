package com.manica.usermanagement.exceptions;

public class UserManagementException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserManagementException(String message) {
        super(message);
    }
}
