package com.eaglebank.account.service;

import com.eaglebank.account.domain.AccountTransactionEvent;
import com.eaglebank.account.domain.dto.AccountBalanceTransactionDTO;
import com.eaglebank.account.domain.dto.AccountResponseDTO;
import com.eaglebank.account.domain.dto.NewAccountDTO;
import com.eaglebank.account.domain.Account;
import com.eaglebank.account.repo.AccountRepo;
import com.eaglebank.account.utils.AccountUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final ApplicationEventPublisher eventPublisher;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final static List<String> availableSortCodes = List.of("10-10-10", "10-10-11", "10-10-12");

    public AccountService(AccountRepo accountRepo, ApplicationEventPublisher eventPublisher) {
        this.accountRepo = accountRepo;
        this.eventPublisher = eventPublisher;
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
            BigDecimal newBalance = account.getBalance().add(BigDecimal.valueOf(Double.valueOf(accountBalanceTransactionDTO.amount())));
            account.setUpdatedTimestamp(Instant.now().toString());
            account.setBalance(newBalance);
            accountRepo.save(account);
            // Send off Kafka event in here if it fails to send etc transactional will roll back the DB.
            eventPublisher.publishEvent(new AccountTransactionEvent());
            return Optional.of(AccountUtils.paddedAccount(account));
        }
        return Optional.empty();
    }
}