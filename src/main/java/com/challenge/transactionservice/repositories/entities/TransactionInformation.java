package com.challenge.transactionservice.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class TransactionInformation {

    @Id
    @Column(unique = true, nullable = false)
    private Long transaction_id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String type;

    private Long parent_id;
}
