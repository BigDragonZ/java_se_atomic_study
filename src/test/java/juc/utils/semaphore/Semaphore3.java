package juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore3 {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(5);
        Thread t1 = new Thread(() -> {
            try {
//                全部获取到可用的所有的许可证
                semaphore.drainPermits();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + "finished");
        });
        t1.start();

        TimeUnit.SECONDS.sleep(2);
        Thread t2 = new Thread(() -> {
            try {
//                全部获取到可用的所有的许可证
                semaphore.acquire(1);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + "finished");
        });
        t2.start();
    }
}
