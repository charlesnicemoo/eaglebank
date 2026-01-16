package com.eaglebank.account.utils;

import com.eaglebank.account.domain.dto.AccountResponseDTO;
import com.eaglebank.account.domain.Account;

public class AccountUtils {

    public static AccountResponseDTO paddedAccount(Account account) {
        Long accountNumberL = account.getAccountNumber();
        // We left pad these because the spec implies that they want account number to start with 01
        if(accountNumberL < 10000000) {
            String zeroLeftPaddedAccountNumber = "0" + account.getAccountNumber();
            return new AccountResponseDTO(
                    zeroLeftPaddedAccountNumber,
                    account.getSortCode(),
                    account.getName(),
                    account.getAccountType(),
                    account.getBalance().toString(),
                    account.getCurrency(),
                    account.getCreatedTimestamp(),
                    account.getUpdatedTimestamp());
        }
        return new AccountResponseDTO(
                Long.toString(account.getAccountNumber()),
                account.getSortCode(),
                account.getName(),
                account.getAccountType(),
                account.getBalance().toString(),
                account.getCurrency(),
                account.getCreatedTimestamp(),
                account.getUpdatedTimestamp());
    }
}