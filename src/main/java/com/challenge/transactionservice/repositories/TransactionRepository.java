package com.challenge.transactionservice.repositories;

import com.challenge.transactionservice.repositories.entities.TransactionInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionInformation, Long> {

    Optional<List<TransactionInformation>> findAllTransactionIdByType(String type);
}
