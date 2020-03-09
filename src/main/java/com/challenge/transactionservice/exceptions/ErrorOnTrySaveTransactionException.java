package com.challenge.transactionservice.exceptions;

public class ErrorOnTrySaveTransactionException extends RuntimeException {

    public ErrorOnTrySaveTransactionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
