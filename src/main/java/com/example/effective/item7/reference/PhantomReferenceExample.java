package com.example.effective.item7.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceExample {

    public static void main(String[] args) throws InterruptedException {
        BigObject strong = new BigObject();
        ReferenceQueue<BigObject> rq = new ReferenceQueue<>();

        PhantomReference<BigObject> phantom = new PhantomReference<>(strong, rq);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        // 팬텀은 죽었지만 사라지지 않고 큐에 들어간다. 본래 객체는 참조해제됨
        System.out.println(phantom.isEnqueued());

        Reference<? extends BigObject> reference = rq.poll();
        reference.clear();
    }
}
