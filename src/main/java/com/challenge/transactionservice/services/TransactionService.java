package com.challenge.transactionservice.services;

import com.challenge.transactionservice.controllers.resources.TransactionRequest;
import com.challenge.transactionservice.controllers.resources.TransactionResponse;
import com.challenge.transactionservice.controllers.resources.TransactionSumResponse;
import com.challenge.transactionservice.exceptions.ErrorOnTrySaveTransactionException;
import com.challenge.transactionservice.exceptions.TransactionNotFoundException;
import com.challenge.transactionservice.exceptions.TransactionsByTypeNotFoundException;
import com.challenge.transactionservice.repositories.TransactionRepository;
import com.challenge.transactionservice.repositories.entities.TransactionInformation;
import com.challenge.transactionservice.repositories.entities.TransactionInformationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionInformationHelper helper;

    public TransactionService(final TransactionRepository repository, final TransactionInformationHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    public TransactionResponse create(final TransactionRequest request) {
        try {
            TransactionInformation transaction = repository.save(helper.fromTransactionRequest(request));
            return helper.fromTransactionInformation(transaction);
        } catch (RuntimeException e) {
            throw new ErrorOnTrySaveTransactionException("Error trying to create a new Transaction with id: "
                    + request.getTransactionId(), e);
        }
    }

    public TransactionResponse getById(final Long id) {
        return helper.fromTransactionInformation(findByID(id));
    }

    public List<Long> getByType(final String type) {
        List<TransactionInformation> transactions = repository.findAllTransactionIdByType(type)
                .orElseThrow(TransactionsByTypeNotFoundException::new);
        return transactions.stream().map(TransactionInformation::getTransaction_id)
                .collect(Collectors.toList());
    }

    public TransactionSumResponse getSumFromTransaction(final Long transactionId) {
        Set<TransactionInformation> allTransactions = getAllTransactions(transactionId);
        return new TransactionSumResponse(allTransactions.stream().mapToDouble(TransactionInformation::getAmount).sum());
    }

    private Set<TransactionInformation> getAllTransactions(final Long id) {
        Set<TransactionInformation> transactions = new HashSet<>();
        Long transactionId = id;

        while (transactionId != null) {
            TransactionInformation transaction = findByID(transactionId);
            transactionId = transaction.getParent_id();
            transactions.add(transaction);
        }
        return transactions;
    }

    private TransactionInformation findByID(final Long transaction_id) {
        return repository.findById(transaction_id).orElseThrow(TransactionNotFoundException::new);
    }
}
