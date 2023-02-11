package item7.executor;

import java.util.concurrent.ExecutionException;

public class ThreadRunnableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();

        // cpu 개수
//        int numberOfCpu = Runtime.getRuntime().availableProcessors();
//        System.out.println("numberOfCpu = " + numberOfCpu);

//        ExecutorService service = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 100; i++) {
//            service.submit(new Task());
//        }


        System.out.println(Thread.currentThread() + " hello");
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " world");
        }
    }
}
