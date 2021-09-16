package com.raquibul.bank.payment.rest.controller.exception;

import com.raquibul.bank.payment.rest.model.Error;
import org.springframework.http.HttpStatus;

public class TransactionFailedException extends ControllerBaseException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public TransactionFailedException() {
        super(Error.TRANSACTION_FAILED_ERROR, HTTP_STATUS);
    }
}
