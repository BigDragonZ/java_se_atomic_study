package juc.utils.semaphore;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaphore2 {

    //    可中断式获取信号量
//    acquireUninterruptibly 获取不可中断的
    @Test
    public void test1() throws InterruptedException {
//        同一时间允许多少个线程获取许可证，也就相当于获取到资源。底层通过AQS完成。
        final Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();

//                semaphore.acquireUninterruptibly();
//                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + "finished");
        });
        t1.start();

        TimeUnit.SECONDS.sleep(50);

        Thread t2 = new Thread(() -> {
            try {
                semaphore.acquire();
//                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + "finished");
        });
        t2.start();

        TimeUnit.SECONDS.sleep(50);
        t2.interrupt();
    }
}
