package com.example.effective.item5.staticutils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {

    @Test
    void isValid() {
        assertTrue(SpellChecker.isValid("test"));
    }

    @Test
    void test() {


        List<B> bList = List.of(new B(true),
                                new B(false),
                                new B(true),
                                new B(false));

        A result = bList.stream()
                .map(A::countOf)
                .reduce(A::aggregationOf)
                .orElseThrow(() -> new RuntimeException("fuck"));

        System.out.println(result.succCnt);
        System.out.println(result.failCnt);
    }


    static class A {
        int succCnt;
        int failCnt;

        A() {}
        A(int succCnt, int failCnt) {
            this.succCnt = succCnt;
            this.failCnt = failCnt;
        }
        public static A countOf(B b) {
            A a = new A();
            if (b.getChecker()) {
                a.succCnt += 1;
            } else {
                a.failCnt += 1;
            }
            return a;
        }

        public static A aggregationOf(A a1, A a2) {
            return new A(a1.succCnt + a2.succCnt, a1.failCnt + a2.failCnt);
        }
    }


    static class B {
        boolean checker;

        B(boolean checker) {
           this.checker = checker;
        }

        public boolean getChecker() {
            return checker;
        }
    }
}