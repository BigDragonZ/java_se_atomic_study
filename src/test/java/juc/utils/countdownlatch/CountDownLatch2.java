package juc.utils.countdownlatch;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatch2 {

    @Test
    public void test1() {
        final CountDownLatch latch = new CountDownLatch(1);
// 例1 里面ExecutorService也可以实现类似需求  CountDownLatch相对而言更加轻量
        new Thread(() -> {
            System.out.println("do some initial working");
            try {
                TimeUnit.SECONDS.sleep(1);
                latch.await();
                System.out.println("do other working");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println(" asyn prepare for some data");
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("do other working");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }

        }).start();
    }
}
