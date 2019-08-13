package juc.utils.readwritewock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLock1 {


    //    参数为true  尽量保证公平 但是不能绝对保证公平
    private final static ReentrantLock lokc = new ReentrantLock(true);
    private final static List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            write();
        });
        t1.start();
    }

    public static void write() {
        try {
            lokc.lock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lokc.unlock();
        }
    }

    public static void read() {
        try {
            lokc.lock();
            data.forEach(System.out::println);
            System.out.println(Thread.currentThread().getName() + "=======");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lokc.unlock();
        }
    }


}
