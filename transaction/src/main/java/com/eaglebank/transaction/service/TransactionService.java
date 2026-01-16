package com.eaglebank.transaction.service;

import com.eaglebank.transaction.domain.Transaction;
import com.eaglebank.transaction.domain.dto.TransactionDTO;
import com.eaglebank.transaction.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    @Cacheable(value = "transactions", key = "#transactionId")
    public Optional<Transaction> getTransactionByTransactionId(Long transactionId) {
        return transactionRepo.findById(transactionId);
    }

    @Transactional
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setAccountNumber(transactionDTO.accountNumber());
        transaction.setAmount(BigDecimal.valueOf(Double.parseDouble(transactionDTO.amount())));
        transaction.setCurrency(transactionDTO.currency());
        transaction.setType(transactionDTO.type());
        // Change this later
        transaction.setUserId(UUID.randomUUID().toString());
        transaction.setReference(transactionDTO.reference());
        transaction.setCreatedTimestamp(Instant.now().toString());
        return transactionRepo.save(transaction);
    }

}