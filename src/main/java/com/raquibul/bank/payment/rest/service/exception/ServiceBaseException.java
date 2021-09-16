package com.raquibul.bank.payment.rest.service.exception;

public abstract class ServiceBaseException extends Exception{
    private final int serviceCode;
    private final int errorCode;

    protected ServiceBaseException(final int serviceCode, final int errorCode){
        this.serviceCode = serviceCode;
        this.errorCode = errorCode;
    }

    protected ServiceBaseException(final int serviceCode, final int errorCode, String message){
        super(message);
        this.serviceCode = serviceCode;
        this.errorCode = errorCode;
    }

    public int getServiceCode(){
        return serviceCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
