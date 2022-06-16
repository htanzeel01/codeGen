package io.swagger.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends Exception {
    HttpStatus status;
    public UnAuthorizedException(HttpStatus status, String message){
        super(message);
        this.status =status;
    }
}
