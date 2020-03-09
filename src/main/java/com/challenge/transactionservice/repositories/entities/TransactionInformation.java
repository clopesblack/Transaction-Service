package com.challenge.transactionservice.repositories.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class TransactionInformation {

    @Id
    private Long transaction_id;
    private Double amount;
    private String type;
    private Long parent_id;
}
