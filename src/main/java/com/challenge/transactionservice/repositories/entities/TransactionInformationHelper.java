package com.challenge.transactionservice.repositories.entities;

import com.challenge.transactionservice.controllers.resources.TransactionRequest;
import com.challenge.transactionservice.controllers.resources.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionInformationHelper {

    public TransactionResponse fromTransactionInformation(TransactionInformation transaction) {
        return TransactionResponse.builder()
                .transaction_id(transaction.getTransaction_id())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parent_id(transaction.getParent_id()).build();
    }

    public TransactionInformation fromTransactionRequest(final TransactionRequest request) {
        return TransactionInformation.builder()
                .transaction_id(request.getTransactionId())
                .amount(request.getAmount())
                .type(request.getType())
                .parent_id(request.getParentId())
                .build();
    }
}
