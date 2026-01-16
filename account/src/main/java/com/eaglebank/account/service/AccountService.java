package com.eaglebank.account.service;

import com.eaglebank.account.domain.dto.AccountBalanceTransactionDTO;
import com.eaglebank.account.domain.dto.AccountResponseDTO;
import com.eaglebank.account.domain.dto.NewAccountDTO;
import com.eaglebank.account.domain.Account;
import com.eaglebank.account.domain.dto.TransactionResponseDTO;
import com.eaglebank.account.repo.AccountRepo;
import com.eaglebank.account.utils.AccountUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final static List<String> availableSortCodes = List.of("10-10-10", "10-10-11", "10-10-12");

    public AccountService(AccountRepo accountRepo, KafkaTemplate<String, Object> kafkaTemplate) {
        this.accountRepo = accountRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Lets cache this for read performance, spare our poor Hikari connection pool some hassle.
    @Cacheable(value = "account", key = "#accountNumber")
    public Optional<AccountResponseDTO> getAccount(String accountNumber) {
        LOGGER.debug("Get account {}", accountNumber);
        // TODO: Remind to let DB exception propagate up, if not found this is normal.
        // But if error we don't want to hide that in a 404 by accident.
        // Not everyone will check log for a 404 response.
        // It becomes more obvious to API user that something else is wrong if 500.
        Optional<Account> accountOptional = accountRepo.findById(Long.valueOf(accountNumber));
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return Optional.of(AccountUtils.paddedAccount(account));
        }
        return Optional.empty();
    }

    // TODO: Remind that transactional triggers on exception so dont necessarily need to wrap it int try/catch optional
    @Transactional
    public AccountResponseDTO createAccount(NewAccountDTO accountDTO) {
        LOGGER.debug("Creating new account {}", accountDTO);
        Account newAccount = new Account();
        newAccount.setName(accountDTO.name());
        newAccount.setAccountType(accountDTO.accountType());
        newAccount.setCurrency(accountDTO.currency());
        // TODO: Remind about IEEE 754, why used HALF_EVEN!
        newAccount.setBalance(new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN));
        newAccount.setSortCode(availableSortCodes.get(0));
        String now = Instant.now().toString(); // Instant is UTC FYI, none of that timezone nonsense
        newAccount.setCreatedTimestamp(now);
        newAccount.setUpdatedTimestamp(now);
        return AccountUtils.paddedAccount(accountRepo.save(newAccount));
    }

    @Transactional
    public Optional<AccountResponseDTO> updateAccountFromTransaction(String accountNumber, AccountBalanceTransactionDTO accountBalanceTransactionDTO) {
        LOGGER.debug("Updating account from Incoming transaction req {}", accountBalanceTransactionDTO);
        Optional<Account> accountOptional = accountRepo.findById(Long.valueOf(accountNumber));
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            BigDecimal newBalance = account.getBalance().add(BigDecimal.valueOf(Double.parseDouble(accountBalanceTransactionDTO.amount())));
            account.setUpdatedTimestamp(Instant.now().toString());
            account.setBalance(newBalance);
            accountRepo.save(account);

            // Keeping here for sake of mentioning, not sure why not working atm. Sort later. Use Rest instead for now.
            // Send off Kafka event here if exception sending message, transactional will roll back.
            //kafkaTemplate.send("transactions", accountBalanceTransactionDTO);

            // This .body() chain should throw exception if 4/500 this keeping data consistent due to transactional
            TransactionResponseDTO transaction = RestClient.create()
                    .post()
                    .uri("http://localhost:9090/v1/transaction")
                    .body(accountBalanceTransactionDTO)
                    .retrieve()
                    .body(TransactionResponseDTO.class);

            return Optional.of(AccountUtils.paddedAccount(account));
        }
        return Optional.empty();
    }
}