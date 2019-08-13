package juc.utils.countdownlatch;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatch3 {

    @Test
    public void test1() throws InterruptedException {
//        为0则立即返回  为负数报错
        final CountDownLatch latch = new CountDownLatch(1);
        Thread thread = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            latch.countDown();
            thread.interrupt();
        }).start();
        latch.await();
        System.out.println("====");
    }
}
