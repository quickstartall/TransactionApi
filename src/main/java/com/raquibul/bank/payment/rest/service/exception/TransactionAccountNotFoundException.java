package com.raquibul.bank.payment.rest.service.exception;

public class TransactionAccountNotFoundException extends TransactionServiceException{
    private static final int ERROR_CODE = 100100;

    public TransactionAccountNotFoundException(){
        super(ERROR_CODE);
    }
}
