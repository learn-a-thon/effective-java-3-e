package com.example.effective.item17;

// 불변 클래스를 보장하는 유연한 방법 => 정적 팩토리를 사용한다.
public class Complex2 {
    private final double re;
    private final double im;

    private Complex2(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex2 valueOf(double re, double im) {
        return new Complex2(re, im);
    }
}
