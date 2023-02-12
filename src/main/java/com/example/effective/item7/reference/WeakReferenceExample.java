package com.example.effective.item7.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class WeakReferenceExample {

    public static void main(String[] args) throws InterruptedException {
        Object strong = new Object();
        WeakReference<Object> weak = new WeakReference<>(strong);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        // 없어진다.
        System.out.println(weak.get());
    }
}
