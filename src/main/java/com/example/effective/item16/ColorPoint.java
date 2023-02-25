package com.example.effective.item16;

// private 중첩 클래스라면 데이터 필드를 노출한다 해도 클래스가 표현하려는 추상 개념만 올바르게 표현해주면 문제가 없다.
public class ColorPoint {
    private static class Point {
        public double x;
        public double y;
    }

    public Point getPoint() {
        Point point = new Point();
        point.x = 1.0;
        point.y = 2.0;

        return point;
    }
}
