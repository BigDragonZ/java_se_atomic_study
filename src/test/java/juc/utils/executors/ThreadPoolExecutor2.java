package juc.utils.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolExecutor2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(20, 40, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(20), r -> {
            Thread thread = new Thread(r);
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());
        IntStream.rangeClosed(0, 20).boxed().forEach(i -> {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " ***" + i + "****");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
// 非阻塞方法，需要等到工作的线程工作完才可以关闭，此时不工作的线程标记一下可以直接关闭
        executor.shutdown();
//        等待所有任务并行结束后执行串行的任务，最多等待一个小时  阻塞
        executor.awaitTermination(1,TimeUnit.HOURS);
//        立即返回，不阻塞 返回队列中的所有任务，核心线程继续完成正在执行的任务
        executor.shutdownNow();

    }
}
