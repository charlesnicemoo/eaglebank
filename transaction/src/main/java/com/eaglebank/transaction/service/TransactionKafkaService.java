package com.eaglebank.transaction.service;

import com.eaglebank.transaction.domain.AccountBalanceTransactionDTO;
import com.eaglebank.transaction.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class TransactionKafkaService {

    @Autowired
    TransactionRepo transactionRepo;

    @KafkaListener(topics = "transactions")
    public void consume(AccountBalanceTransactionDTO event) {
        try {
            System.out.println("received event: " + event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}