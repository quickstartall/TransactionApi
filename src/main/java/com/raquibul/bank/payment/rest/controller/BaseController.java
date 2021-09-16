package com.raquibul.bank.payment.rest.controller;

import com.raquibul.bank.payment.rest.model.Error;
import com.raquibul.bank.payment.rest.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This is the base class to be extended by all the controllers. It has common methods used in other Controllers
 */
class BaseController {

    /**
     * This method builds appropriate Error Response from the provided {@link Error} and Httpstatus
     * @param error {@link Error}
     * @param httpStatus httpStatus
     * @return the Error response built from the provided {@link Error} and HttpStatus
     */
    protected ResponseEntity<?> buildError(Error error, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(String.valueOf(error.getCode()));
        errorResponse.setErrorMessage(error.getMessage());
        errorResponse.setSeverity(error.getSeverity());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * This methds builds the response from provided response body and HttpStatus
     * @param body respnse body
     * @param httpStatus HttpStatus
     * @param <T> type of the Response body
     * @return Response build from the provided body and HttpStatus
     */
    protected <T> ResponseEntity<T> buildResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<T>(body, httpStatus);
    }
}
