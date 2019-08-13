package juc.utils.condition;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition1 {
    private static final ReentrantLock lock = new ReentrantLock(true);

    private final static Condition CONDITION = lock.newCondition();


    private static int data = 0;

    private static volatile boolean noUse = true;

    public static void main(String[] args) {
        new Thread(()->{
            while (true){
                buildData();
            }
        }).start();
        new Thread(()->{
            while (true){
                useData();
            }
        }).start();
    }
    private static void buildData() {
        try {
            lock.lock();// 相当于 synchronized 关键字  monitor enter
            while (noUse) {
                CONDITION.await();  //  monitor.wait
            }
            data++;
            Optional.of("P" + data).ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
            noUse = true;
            CONDITION.signal();  // monitor.notify
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//  synchronized   end  monitor exit
        }
    }

    private static void useData() {
        try {
            lock.lock();
            while (!noUse) {
                CONDITION.await();
            }

            TimeUnit.SECONDS.sleep(1);
            Optional.of("C" + data).ifPresent(System.out::println);
            noUse = false;
            CONDITION.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
