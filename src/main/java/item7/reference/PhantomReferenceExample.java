package item7.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceExample {
    public static void main(String[] args) throws InterruptedException {
        BigObject strong = new BigObject();
        ReferenceQueue<BigObject> rq = new ReferenceQueue<>();

//        BigObjectReference<BigObject> phantom = new BigObjectReference<>(strong, rq);

        // PhantomReference의 생성자는 ReferenceQueue를 넘겨줘야한다.
        // GC가 발생하면 본래 가지고 있던 레퍼런스는 정리하고(null 이기 때문)
        // phantom 레퍼런스는 ReferenceQueue에 넣어준다.
        PhantomReference<BigObject> phantom = new PhantomReference<>(strong, rq);

        strong = null;

        System.gc();
        Thread.sleep(3000L);

        // TODO 팬텀은 유령이니까..
        //  죽었지만.. 사라지진 않고 큐에 들어갑니다.
        System.out.println(phantom.isEnqueued());

        Reference<? extends BigObject> reference = rq.poll();
        BigObjectReference bigObjectCleaner = (BigObjectReference) reference;
        bigObjectCleaner.cleanUp();
        // clear를 직접 해줘야 phantom reference가 사라진다.
        reference.clear();
    }
}
