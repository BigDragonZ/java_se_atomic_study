package juc.utils.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutor1 {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) buildThreadPoolExecutor();
        while (true) {
            System.out.println(executorService.getActiveCount());
            System.out.println(executorService.getCorePoolSize());
            System.out.println(executorService.getQueue());
            System.out.println(executorService.getMaximumPoolSize());
        }
    }

    /**
     * int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue,
     * ThreadFactory threadFactory,
     * RejectedExecutionHandler handler
     */

    private static ExecutorService buildThreadPoolExecutor() {
        ExecutorService executor = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), r -> {
            Thread thread = new Thread(r);
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());
        System.out.println(" the ThreadPoolExecutor create dene");
        executor.execute(() -> sleep(100));
        return executor;
    }

    private static void sleep(long second) {
        try {
            System.out.println(Thread.currentThread().getName() + "******");
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
