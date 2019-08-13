package juc.utils.cycilcbarried;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrier1 {

    @Test
    public void test(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
                System.out.println(" t1 finished");
                cyclicBarrier.await();
                System.out.println(" other thread finished too");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(" t2 finished");
                cyclicBarrier.await();
                System.out.println("t2 other thread finished too");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
