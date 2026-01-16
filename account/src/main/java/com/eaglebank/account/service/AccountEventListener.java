package com.eaglebank.account.service;

import com.eaglebank.account.domain.AccountTransactionEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AccountEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBalanceUpdate(AccountTransactionEvent event) {
        System.out.println("SENDING TRANSACTION EVENT");
        //        kafkaTempl.send("balance-updates", event.getAccount().getAccountNumber(), event);
    }
}