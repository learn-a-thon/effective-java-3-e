package item7.executor;

import java.util.concurrent.*;

public class ExecutorsExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
//        ExecutorService service = Executors.newCachedThreadPool();
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        ExecutorService service = Executors.newScheduledThreadPool(10);

        Future<String> submit = service.submit(new Task()); // non blocking call

        System.out.println(Thread.currentThread() + " hello"); // non blocking call

        System.out.println(submit.get()); // blocking call

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
