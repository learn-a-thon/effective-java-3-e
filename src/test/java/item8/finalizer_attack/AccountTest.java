package item8.finalizer_attack;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountTest {

    @Test
    void 일반_계정() {
        Account account = new Account("hello");
        account.transfer(BigDecimal.valueOf(1004), "good");
    }

    @Test
    void 푸틴_계정() {
        Account account = new Account("푸틴");
        account.transfer(BigDecimal.valueOf(9999), "bad");
    }

    @Test
    void 푸틴_계정_Broker() throws InterruptedException {
        Account account = null;
        try {
            account = new BrokenAccount("푸틴");
        } catch (Exception e) {
            System.out.println("exception occur!");
        }
        System.gc();
        Thread.sleep(3000L);
    }
}
