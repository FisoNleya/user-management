package com.manica.usermanagement.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public TokenAuthenticationException(String message) {
        super(message);
    }
}
