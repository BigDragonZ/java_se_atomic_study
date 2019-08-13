package juc.utils.cycilcbarried;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class CyclicBarrier3 {

    static class MyCountDownLatch extends CountDownLatch {


        private final Runnable runnable;

        /**
         * Constructs a {@code CountDownLatch} initialized with the given count.
         *
         * @param count the number of times {@link #countDown} must be invoked
         *              before threads can pass through {@link #await}
         * @throws IllegalArgumentException if {@code count} is negative
         */
        public MyCountDownLatch(int count, Runnable runnable) {
            super(count);
            this.runnable = runnable;
        }


        public void countDown() {
            super.countDown();
            if (getCount() == 0) {
                this.runnable.run();
            }
        }
    }

    @Test
    public void test1() {
        final MyCountDownLatch myCountDownLatch = new MyCountDownLatch(2, () -> {
            System.out.println(" all of work finished ");
        });
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myCountDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myCountDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();
    }

    public static void main(String[] args) {
        final MyCountDownLatch myCountDownLatch = new MyCountDownLatch(2, () -> {
            System.out.println(" all of work finished ");
        });
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myCountDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myCountDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();
    }
}
