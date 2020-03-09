package com.challenge.transactionservice.controllers.resources;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TransactionRequest {

    @NotNull
    @JsonAlias(value = "transaction_id")
    private Long transactionId;

    @NotNull
    private Double amount;

    @NotNull
    private String type;

    @JsonAlias(value = "parent_id")
    private Long parentId;
}
