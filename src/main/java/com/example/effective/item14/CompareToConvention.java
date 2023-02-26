package com.example.effective.item14;

import java.math.BigDecimal;

public class CompareToConvention {

    public static void main(String[] args) {

        BigDecimal n1 = BigDecimal.valueOf(23134134);
        BigDecimal n2 = BigDecimal.valueOf(23134135);
        BigDecimal n3 = BigDecimal.valueOf(23134136);
        BigDecimal n4 = BigDecimal.valueOf(23134137);

        // p88 반사성
        System.out.println(n1.compareTo(n1));

        // p88 대칭성
        System.out.println(n2.compareTo(n1));
        System.out.println(n1.compareTo(n2));

        // p89 추이성
        System.out.println(n3.compareTo(n1) > 0);
        System.out.println(n1.compareTo(n2) > 0);
        System.out.println(n3.compareTo(n2) > 0);

        // p89 일관성
        System.out.println(n4.compareTo(n2));
        System.out.println(n2.compareTo(n1));
        System.out.println(n4.compareTo(n1));

        // p89, compareTo가 0이라면 equals 는 true 여야 한다. (다르다면 명세하는 것을 권장)
        BigDecimal oneZero = new BigDecimal("1.0");
        BigDecimal oneZeroZero = new BigDecimal("1.00");
        System.out.println(oneZero.compareTo(oneZeroZero));
        System.out.println(oneZero.equals(oneZeroZero));

    }
}
