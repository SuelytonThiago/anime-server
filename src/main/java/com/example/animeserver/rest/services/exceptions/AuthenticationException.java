package com.example.animeserver.rest.services.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends BaseBusinessException{

    public AuthenticationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
