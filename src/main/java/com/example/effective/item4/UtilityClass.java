package com.example.effective.item4;

// 추상 클래스로 생성을 제한하는 방법은 충분하지 않다.
public class UtilityClass {

    /**
     * 이 클래스는 인스턴스를 만들 수 없다.
     */
    private UtilityClass() {
        throw new AssertionError();
    }

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        String hello = UtilityClass.hello();

        UtilityClass utilityClass = new UtilityClass();
    }
}
