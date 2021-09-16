package com.raquibul.bank.payment.rest.controller.exception;

import com.raquibul.bank.payment.rest.model.Error;
import com.raquibul.bank.payment.rest.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * This Exception Handler will intercept the matching exceptions and build appropriate Error response
 *
 * @see ExceptionHandler
 * @see ControllerAdvice
 */
@ControllerAdvice
public class TransactionExceptionHandler {
    /**
     * Intercepts the exceptions of type ControllerBaseException and transforms it to appropriate error response
     *
     * @param exception exception of type {@link ControllerBaseException}
     * @return ResponseEntity - Error Response built from the ControllerBaseException
     */
    @ExceptionHandler(value = ControllerBaseException.class)
    public ResponseEntity<?> handleApplicationException(ControllerBaseException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(String.valueOf(exception.getError().getCode()));
        errorResponse.setErrorMessage(exception.getError().getMessage());
        errorResponse.setSeverity(exception.getError().getSeverity());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }

    /**
     * Intercepts the exceptions of type {@link Exception} and transforms it to appropriate error response
     *
     * @param exception exception of type {@link Exception}
     * @return ResponseEntity - Error Response built from the {@link Exception}
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(Error.UNKNOWN_ERROR.getCode());
        errorResponse.setErrorMessage(Error.UNKNOWN_ERROR.getMessage());
        errorResponse.setSeverity(Error.UNKNOWN_ERROR.getSeverity());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
