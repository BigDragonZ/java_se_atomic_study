package juc.utils.reentrantLock;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLock1 {


    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        IntStream.range(0, 2).forEach(i -> new Thread(() -> {
//            needLock();
//        }).start());

        Thread t1 = new Thread(() -> testUnInterruptbly());
        t1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> testUnInterruptbly());
        t2.start();
        TimeUnit.SECONDS.sleep(1);

        t2.interrupt();
    }


    /**
     * 该方法属于显式锁，作用相当于needLockBySync方法，但是在性能上会比synchronized好一点。
     * 显式锁的另一个好处时可以尝试去获取锁，获取不到时当前线程不进入阻塞状态。
     */
    public static void needLock() {
        try {
            lock.lock();// 不可以被打断
//            此处休眠相当于当前线程去执行自己的任务
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void testUnInterruptbly() {
        try {
            lock.lockInterruptibly();// 允许被打断
//            此处休眠相当于当前线程去执行自己的任务
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void needLockBySync() {
        synchronized (ReentrantLock1.class) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
