package com.raquibul.bank.payment.rest.controller.exception;

import com.raquibul.bank.payment.rest.model.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ControllerBaseException extends RuntimeException{
    private final Error error;
    private final HttpStatus httpStatus;

    protected ControllerBaseException(final Error error, final HttpStatus httpStatus){
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
