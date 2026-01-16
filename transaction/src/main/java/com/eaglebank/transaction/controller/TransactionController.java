package com.eaglebank.transaction.controller;

import com.eaglebank.transaction.domain.Transaction;
import com.eaglebank.transaction.domain.dto.TransactionDTO;
import com.eaglebank.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable String transactionId) {
        return ResponseEntity.ok(new Transaction());
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDTO));
    }
}