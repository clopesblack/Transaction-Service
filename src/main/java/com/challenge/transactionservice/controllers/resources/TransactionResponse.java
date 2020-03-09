package com.challenge.transactionservice.controllers.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionResponse {

    private Long transaction_id;
    private Double amount;
    private String type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long parent_id;
}
