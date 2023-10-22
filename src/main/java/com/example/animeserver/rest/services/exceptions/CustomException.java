package com.example.animeserver.rest.services.exceptions;

import com.example.animeserver.rest.services.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class CustomException extends BaseBusinessException {

    public CustomException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
