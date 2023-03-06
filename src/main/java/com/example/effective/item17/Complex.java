package com.example.effective.item17;

public class Complex {
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    // 자신을 수정하지 않고 새로운 Complex 인스턴스를 만들어 반환한다.
    // add 와 같은 동사 대신 plus 전치사를 사용한 것은 불변이라는 것을 의도한 네이밍이다.
    public Complex plus(Complex complex) {
        return new Complex(re + complex.re, im + complex.im);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Complex)) {
            return false;
        }
        Complex c = (Complex) o;

        // 참조 타입에는 == 대신 equals 사용
        return Double.compare(c.re, re) == 0
                && Double.compare(c.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
        return "(" + re + "+" + im + "i)";
    }
}
