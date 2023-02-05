package item8.finalizer_attack;

import java.math.BigDecimal;

public class Account {
    private String accountId;

    public Account(String accountId) {
        this.accountId = accountId;

        if ("푸틴".equals(accountId)) {
            throw new IllegalArgumentException("푸틴은 계정을 막습니다.");
        }
    }

    public void transfer(BigDecimal amount, String to) {
        System.out.printf("transfer %f from %s to %s\n", amount, accountId, to);
    }

    // finalize 메소드를 final로 선언함으로서
    // 하위 객체에서 finalize를 사용하지 못하도록 해서 방어할 수 있다.
//    @Override
//    protected final void finalize() throws Throwable {
//        // 아무 행위를 하지 않아야 한다.
//    }
}
