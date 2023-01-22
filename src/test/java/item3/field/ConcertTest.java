package item3.field;

import org.junit.jupiter.api.Test;

class ConcertTest {

    @Test
    void perform() {
        Concert concert = new Concert(new IElvis() {
            @Override
            public void sing() {
                System.out.println("mock singing~");
            }
        });
        concert.perform();
    }
}
