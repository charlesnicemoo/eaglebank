package com.eaglebank.account.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Account {

    /* TODO: Start with numbers already padded.
     Could promote sort code to id and increment sort codes if we ever hit 99999999
     If eaglebank ran out of assigned sort codes...
     Potentially, we can loop back to 00000001 and then pad out the value if need be. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "acc_gen", sequenceName = "acc_seq", initialValue = 1000000)
    private Long accountNumber;
    private String sortCode;
    private String name;
    private String accountType;
    // Avoid IEEE 754! float/double are lossy since using mantissa and exponent.
    // Also, if store as BD we can access and do calcs directly in SQL if needed
    private BigDecimal balance;
    private String currency;
    private String createdTimestamp;
    private String updatedTimestamp;

    public Account() {

    }

    public Account(Long accountNumber, String sortCode, String name, String accountType, BigDecimal balance, String currency, String createdTimestamp, String updatedTimestamp) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.name = name;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
}