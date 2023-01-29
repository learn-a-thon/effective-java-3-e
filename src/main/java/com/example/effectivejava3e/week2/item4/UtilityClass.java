package com.example.effectivejava3e.week2.item4;

/**
 * 인스턴스 생성 불가
 */

public class UtilityClass {

    private UtilityClass() {
        throw new AssertionError();
    }

    public static String hello() {
        return "hello";
    }
}
