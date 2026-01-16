package com.eaglebank.aggregate.service;

import com.eaglebank.aggregate.domain.dto.account.AccountDTO;
import com.eaglebank.aggregate.domain.dto.account.NewAccountDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class AggregateAccountService {

    private final WebClient.Builder webClientBuilder;

    public AggregateAccountService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    // TODO: Remind about service discovery like eureka, would not use hardcoded URL for production.
    // Also point to LB
    @Cacheable(value = "accounts", key = "#accountNumber")
    public Optional<AccountDTO> getAccountByAccountNumber(String accountNumber) {
        // Block here since need the output to return back
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8090/v1/account/{accountId}", accountNumber)
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .blockOptional();
    }

    public AccountDTO createAccount(NewAccountDTO newAccountDTO) {
        AccountDTO accountDTO = webClientBuilder.build()
                .post()
                .uri("http://localhost:8090/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newAccountDTO)
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .block();

        return accountDTO;
    }

    // TODO: Remind that this purges cache when updating account so no stale account data
    @CacheEvict(value = "accounts", key = "#accountNumber")
    public void updateAccount(String accountNumber, AccountDTO updated) {
        //fill later
        return;
    }
}