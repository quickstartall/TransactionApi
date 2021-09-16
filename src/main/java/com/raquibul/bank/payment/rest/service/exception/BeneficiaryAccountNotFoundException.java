package com.raquibul.bank.payment.rest.service.exception;

public class BeneficiaryAccountNotFoundException extends TransactionServiceException{
    private static final int ERROR_CODE = 100101;

    public BeneficiaryAccountNotFoundException(){
        super(ERROR_CODE);
    }
}
