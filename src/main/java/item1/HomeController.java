package item1;

import java.math.BigInteger;
import java.util.Random;

public class HomeController {

    public static void main(String[] args) {
        BigInteger bigInteger1 = new BigInteger(10, new Random());
        BigInteger bigInteger2 = BigInteger.probablePrime(10, new Random());// java 4 추가
        System.out.println("bigInteger1 = " + bigInteger1);
        System.out.println("bigInteger2 = " + bigInteger2);
    }
}
