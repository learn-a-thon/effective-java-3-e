package com.example.effectivejava3e.week7.item20.multipleinheritance;

public class AbstractFlyable implements Flyable {

    @Override
    public void fly() {
        System.out.println("너랑 딱 붙어있을게!");
    }
}
