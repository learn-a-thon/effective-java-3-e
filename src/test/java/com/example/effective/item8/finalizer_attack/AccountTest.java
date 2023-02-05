package com.example.effective.item8.finalizer_attack;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void 일반_계정() {
        Account account = new Account("test");
        account.transfer(BigDecimal.valueOf(10.4), "hello");
    }

    @Test
    public void 푸틴_계정() throws InterruptedException {
        Account account = null;
        try {
            account = new BrokenAccount("푸틴");
        } catch (Exception e) {
            System.out.println("??");
        }

        System.gc();
        Thread.sleep(3000L);
    }
}