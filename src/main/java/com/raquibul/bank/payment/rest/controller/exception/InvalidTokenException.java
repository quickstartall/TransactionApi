package com.raquibul.bank.payment.rest.controller.exception;

import com.raquibul.bank.payment.rest.model.Error;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ControllerBaseException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidTokenException() {
        super(Error.INVALID_TOKEN_ERROR, HTTP_STATUS);
    }
}
