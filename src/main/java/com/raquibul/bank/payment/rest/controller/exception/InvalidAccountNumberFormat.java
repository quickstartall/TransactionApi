package com.raquibul.bank.payment.rest.controller.exception;

import com.raquibul.bank.payment.rest.model.Error;
import org.springframework.http.HttpStatus;

public class InvalidAccountNumberFormat extends ControllerBaseException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidAccountNumberFormat() {
        super(Error.INVALID_ACCOUNT_FORMAT_ERROR, HTTP_STATUS);
    }
}
