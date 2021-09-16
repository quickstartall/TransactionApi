package com.raquibul.bank.payment.rest.service.exception;

public abstract class TransactionServiceException extends ServiceBaseException {
    private static final int SERVICE_CODE = 100;

    public TransactionServiceException(final int errorCode) {
        super(SERVICE_CODE, errorCode);
    }

    public TransactionServiceException(final int errorCode, final String message) {
        super(SERVICE_CODE, errorCode);
    }
}
