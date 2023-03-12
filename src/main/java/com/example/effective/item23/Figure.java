package com.example.effective.item23;

public class Figure {
    enum Shape {
        RECTANGLE, CIRCLE
    }

    // 태그 팔드 - 현재 모양을 나타낸다.
    final Shape shape;

    double length;
    double width;

    // 다음 필드는 모양이 원 Circle 일 때만 쓰인다.
    double radius;

    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // 사각형 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
