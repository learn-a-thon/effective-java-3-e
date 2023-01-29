package com.example.effectivejava3e.week2.item6;

public class Strings {

    public static void main(String[] args) {
        String hello = "hello";

        String hello2 = new String("hello");

        String hello3 = "hello";

        System.out.println(hello == hello2);
        System.out.println(hello.equals(hello2));
        System.out.println(hello == hello3);
        System.out.println(hello.equals(hello3));
    }
}
