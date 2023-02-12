package com.example.effective.item7.executor;

import java.util.concurrent.*;

public class ExecutorsExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        Future<String> submit = service.submit(new Task()); // 논블로킹

        System.out.println(Thread.currentThread() + " hello");

        System.out.println(submit.get()); // 블로킹

        service.shutdown();
    }

    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000L);
            return Thread.currentThread() + " world";
        }
    }

}
