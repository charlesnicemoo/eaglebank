package com.eaglebank.aggregate.controller;
import com.eaglebank.aggregate.domain.dto.account.AccountDTO;
import com.eaglebank.aggregate.domain.dto.account.NewAccountDTO;
import com.eaglebank.aggregate.domain.dto.account.UpdateAccountDTO;
import com.eaglebank.aggregate.service.AggregateAccountService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/accounts")
public class AccountsAggregateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsAggregateController.class);

    @Autowired
    AggregateAccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody NewAccountDTO newAccountDTO) {
        LOGGER.debug("Creating new account {}", newAccountDTO);
        AccountDTO newAccount = accountService.createAccount(newAccountDTO);
        URI location = URI.create("http://localhost:63342/v1/accounts/" + newAccount.accountNumber());
        return ResponseEntity.created(location).body(newAccount);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> listAccounts() {
        LOGGER.debug("Listing accounts");
        List<AccountDTO> accounts = new ArrayList<>();
        accounts.add(
                new AccountDTO("accountNumber",
                        "sortCode",
                        "testName",
                        "Personal",
                        "0.00",
                        "GBP",
                        "created",
                        "updated"));
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}")
    //@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AccountDTO> fetchAccountByAccountNumber(
            @PathVariable("accountNumber")
            @Pattern(regexp = "^01\\d{6}$")
            String accountNumber) {
        LOGGER.debug("Fetching account {}", accountNumber);
        Optional<AccountDTO> accountDTOOptional = accountService.getAccountByAccountNumber(accountNumber);
        return accountDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO: Remember to add the -X PATCH and -H "Content-Type: application/json" in da curl
    @PatchMapping("/{accountNumber}")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AccountDTO> updateAccountByAccountNumber(
            @PathVariable("accountNumber")
            @Pattern(regexp = "^01\\d{6}$") String accountNumber,
            @RequestBody UpdateAccountDTO updateAccountDTO) {
        LOGGER.debug("Updating account {}", accountNumber);
        AccountDTO updatedAccount = new AccountDTO(accountNumber,
                "sortCode",
                updateAccountDTO.name(),
                updateAccountDTO.accountType(),
                "0.00",
                "GBP",
                "created",
                "updated");
        return ResponseEntity.ok(updatedAccount);
    }

    // TODO: Remember -X DELETE in the curl. ALSO add -I to see 204 header, -v verbose also works.
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccountbyAccountNumber(@PathVariable String accountNumber){
        LOGGER.debug("Deleting account {}", accountNumber);
        return ResponseEntity.noContent().build();
    }
}