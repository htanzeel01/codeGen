package io.swagger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginException extends ResponseStatusException {
    public LoginException(HttpStatus status, String reason){
        super(status, reason);
    }
}
