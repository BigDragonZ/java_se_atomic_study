package juc.utils.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Executors1 {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
    }


    private static void userCachedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());


        executorService.execute(() -> System.out.println("===="));
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        IntStream.rangeClosed(0, 100).boxed().forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " [" + i + "]");
            });
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
    }

    private static void useFixedSizePool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.rangeClosed(0, 100).boxed().forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " [" + i + "]");
            });
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
    }

    private static void newSingleThreadExecutor() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        IntStream.rangeClosed(0, 100).boxed().forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " [" + i + "]");
            });
        });

        TimeUnit.SECONDS.sleep(1);
    }
}
