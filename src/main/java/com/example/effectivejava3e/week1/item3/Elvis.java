package com.example.effectivejava3e.week1.item3;

public class Elvis {

    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    private static Elvis getInstance() {
        return INSTANCE;
    } // 두번째 방법

    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
    }
}
