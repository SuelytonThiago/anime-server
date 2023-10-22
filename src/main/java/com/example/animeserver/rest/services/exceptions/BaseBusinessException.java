package com.example.animeserver.rest.services.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseBusinessException extends RuntimeException{
    public BaseBusinessException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();
}
