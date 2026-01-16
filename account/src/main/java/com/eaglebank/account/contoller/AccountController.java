package com.eaglebank.account.contoller;

import com.eaglebank.account.domain.dto.AccountBalanceTransactionDTO;
import com.eaglebank.account.domain.dto.AccountResponseDTO;
import com.eaglebank.account.domain.dto.NewAccountDTO;
import com.eaglebank.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<AccountResponseDTO> getAccountByAccountNumber(@PathVariable String accountNumber) {
        Optional<AccountResponseDTO> accountOptional = accountService.getAccount(accountNumber);
        return accountOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/account")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody NewAccountDTO account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @PatchMapping("/account/{accountNumber}/updateBalance")
    public ResponseEntity<AccountResponseDTO> updateAccountBalance(@PathVariable String accountNumber,
                                                                   @RequestBody AccountBalanceTransactionDTO
                                                                           accountBalanceTransactionDTO) {
        Optional<AccountResponseDTO> accountResponseDTOOptional =
                accountService.updateAccountFromTransaction(accountNumber, accountBalanceTransactionDTO);
        return accountResponseDTOOptional.map(
                ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO: patch and delete

}