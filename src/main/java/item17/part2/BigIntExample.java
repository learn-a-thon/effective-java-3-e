package item17.part2;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class BigIntExample {

    public static void main(String[] args) {
        BigInteger ten = BigInteger.TEN;
        BigInteger minusTen = ten.negate();

        final Set<Point> points = new HashSet<>();
        Point firstPoint = new Point(1, 2);
        points.add(firstPoint);

        // 불변 클래스이기 때문에 변경 불가능
        // firstPoint.x = 10;
    }

    static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
