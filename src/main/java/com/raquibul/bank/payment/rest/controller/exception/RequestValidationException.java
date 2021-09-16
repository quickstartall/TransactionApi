package com.raquibul.bank.payment.rest.controller.exception;

import com.raquibul.bank.payment.rest.model.Error;
import org.springframework.http.HttpStatus;

public class RequestValidationException extends ControllerBaseException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public RequestValidationException() {
        super(Error.ACCOUNT_NOT_FOUND_ERROR, HTTP_STATUS);
    }
}
