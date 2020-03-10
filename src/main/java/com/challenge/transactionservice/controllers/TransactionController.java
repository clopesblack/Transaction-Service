package com.challenge.transactionservice.controllers;

import com.challenge.transactionservice.controllers.resources.TransactionRequest;
import com.challenge.transactionservice.controllers.resources.TransactionResponse;
import com.challenge.transactionservice.controllers.resources.TransactionSumResponse;
import com.challenge.transactionservice.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private final TransactionService service;

    public TransactionController(final TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody @Valid final TransactionRequest request) {
        return ResponseEntity.status(CREATED).body(service.create(request));
    }

    @GetMapping("/{transaction_id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable final Long transaction_id) {
        return ResponseEntity.ok(service.getById(transaction_id));
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<Long>> getByType(@PathVariable final String type) {
        return ResponseEntity.ok(service.getByType(type));
    }

    @GetMapping("/sum/{transaction_id}")
    public ResponseEntity<TransactionSumResponse> getSumByTransaction(@PathVariable final Long transaction_id) {
        return ResponseEntity.ok(service.getSumFromTransaction(transaction_id));
    }
}
