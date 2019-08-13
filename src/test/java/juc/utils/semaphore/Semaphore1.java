package juc.utils.semaphore;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore1 {
    /**
     * 信号量
     */
    @Test
    public void test1() {
        final SemaphoreLock lock = new SemaphoreLock();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is running");
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get the lock");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

                System.out.println(Thread.currentThread().getName() + " release the lock");
            }).start();
        }
    }

    static class SemaphoreLock {
        private final Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }
}
