package com.example.effectivejava3e.week7.item21;

/**
 * 클래스가 무조건 인터페이스를 이긴다.
 * 그래서 여기서 hello()는 슈퍼 클래스의 Hello()를 호출한다. (Runtime Error 발생)
 */
public class SubClass extends SuperClass implements MarkerInterface {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        subClass.hello();
    }
}

