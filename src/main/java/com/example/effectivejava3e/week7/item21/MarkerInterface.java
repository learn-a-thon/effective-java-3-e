package com.example.effectivejava3e.week7.item21;

public interface MarkerInterface {

    default void hello() {
        System.out.println("hello interface");
    }

}
