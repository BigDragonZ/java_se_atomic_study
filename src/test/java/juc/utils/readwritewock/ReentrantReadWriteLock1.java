package juc.utils.readwritewock;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLock1 {
    private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final static Lock readLock = readWriteLock.readLock();

    private final static Lock writeLock = readWriteLock.writeLock();

    private final static List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(ReentrantReadWriteLock1::read);
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(ReentrantReadWriteLock1::read);
        t2.start();

    }

    public static void write() {
        try {
            writeLock.lock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void read() {
        try {
            readLock.lock();
            data.forEach(System.out::println);
            System.out.println(Thread.currentThread().getName() + "=======");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

}
