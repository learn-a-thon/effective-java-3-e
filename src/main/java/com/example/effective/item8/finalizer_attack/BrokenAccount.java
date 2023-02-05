package com.example.effective.item8.finalizer_attack;

import java.math.BigDecimal;

public class BrokenAccount extends Account{

    private String accountId;

    public BrokenAccount(String accountId) {
        super(accountId);
    }

    public void transfer(BigDecimal amount, String to) {
        this.transfer(BigDecimal.valueOf(1000000),"test");
    }
}
