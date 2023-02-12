package com.example.effective.item7.reference;

import java.lang.ref.SoftReference;

public class SoftReferenceExample {

    public static void main(String[] args) throws InterruptedException {
        Object strong = new Object();
        SoftReference<Object> soft = new SoftReference<>(strong);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        // 메모리자 충분해서 거의 안없어진다. soft하게.
        System.out.println(soft.get());
    }
}
