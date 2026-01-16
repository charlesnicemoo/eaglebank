package com.eaglebank.aggregate.controller;

import com.eaglebank.aggregate.domain.dto.transaction.CreateTransactionDTO;
import com.eaglebank.aggregate.domain.dto.transaction.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class TransactionAggregateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionAggregateController.class);

    @PostMapping("/{accountNumber}/transactions")
    public ResponseEntity<CreateTransactionDTO>createTransaction(@PathVariable String accountNumber, @RequestBody CreateTransactionDTO createTransactionDTO){
        LOGGER.debug("Creating transaction for account {}", accountNumber);
        return ResponseEntity.ok(createTransactionDTO);
    }

    @GetMapping("/{accountNumber}/transations")
    public ResponseEntity<List<TransactionDTO>> listAccountTransaction(@PathVariable String accountNumber) {
        LOGGER.debug("Listing transactions for account {}", accountNumber);
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{accountNumber}/transactions/{transactionId}")
    public ResponseEntity<TransactionDTO> fetchAccountTransactionByID(@PathVariable String accountNumber, @PathVariable String transactionId) {
        LOGGER.debug("Fetching transaction for account {}", accountNumber);
        return ResponseEntity.ok(new TransactionDTO());
    }
}