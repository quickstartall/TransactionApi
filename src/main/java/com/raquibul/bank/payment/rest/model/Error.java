package com.raquibul.bank.payment.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    ACCOUNT_NOT_FOUND_ERROR("200102", "The Source Account Could not be found", Severity.HIGH),
    BENEFICIARY_NOT_FOUND_ERROR("200103", "The Beneficiary Account Could not be found", Severity.MEDIUM),
    INVALID_ACCOUNT_FORMAT_ERROR("200101", "The Account format is wrong", Severity.LOW),
    TRANSACTION_FAILED_ERROR("200104", "Transaction failed.", Severity.HIGH),
    REQUEST_VALIDATION_ERROR("200105", "Request validation failed.", Severity.LOW),
    INVALID_TOKEN_ERROR("300101", "Either invalid or expired toke. Please retry with valid token", Severity.LOW),
    AUTHENTICATION_ERROR("300102", "Authentication failed. Check request and retry", Severity.LOW),
    UNKNOWN_ERROR("300100", "There was some unknown error.", Severity.HIGH);;
    private final String code;
    private final String message;
    private final Severity severity;
}
