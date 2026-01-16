package com.eaglebank.transaction.service;

import com.eaglebank.transaction.domain.Transaction;
import com.eaglebank.transaction.domain.dto.TransactionDTO;
import com.eaglebank.transaction.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    // TODO: think of how to improve cache transactions, since 1:many relationship from acc.
    //  Will use SB internal cache for now, but lets explore caffeine cache later.
    //  We can boost perf this way especially since transactions are not often modified.

    @Cacheable(value = "transactions", key = "#transactionId")
    public Optional<Transaction> getTransactionByTransactionId(Long transactionId) {
        return transactionRepo.findById(transactionId);
    }

    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) {
        return new Transaction();
    }

}