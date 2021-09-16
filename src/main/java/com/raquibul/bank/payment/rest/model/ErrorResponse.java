package com.raquibul.bank.payment.rest.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private Severity severity;
    private String errorCode;
    private String errorMessage;
}
