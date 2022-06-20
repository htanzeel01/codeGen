package io.swagger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnAuthorizedException extends ResponseStatusException {
    public UnAuthorizedException(HttpStatus status, String reason){
        super(status, reason);
    }
}
