package juc.utils.condition;

import sun.awt.SunHints;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Condition3 {
    private final static ReentrantLock LOCK = new ReentrantLock();
    private final static Condition PRODECE_COND = LOCK.newCondition();

    private final static Condition CONSUME_COND = LOCK.newCondition();


    private final static LinkedList<Long> TIMESTAMP_POOL = new LinkedList<>();

    private final static int MAX_CAPCITY = 100;

    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0, 6).boxed().forEach(Condition3::beginProduce);
        IntStream.range(0, 15).boxed().forEach(Condition3::beginConsume);
//        看一下数据
        for (; ; ) {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("==================");
            System.out.println("PRODECE_COND  getWaitQueueLength:" + LOCK.getWaitQueueLength(PRODECE_COND));
            System.out.println("CONSUME_COND getWaitQueueLength:" + LOCK.getWaitQueueLength(CONSUME_COND));

            System.out.println("PRODECE_COND hasWaiters:" + LOCK.hasWaiters(PRODECE_COND));
            System.out.println("CONSUME_COND hasWaiters:" + LOCK.hasWaiters(CONSUME_COND));

            System.out.println("PRODECE_COND :" + LOCK.getWaitQueueLength(PRODECE_COND));
            System.out.println("CONSUME_COND :" + LOCK.getWaitQueueLength(CONSUME_COND));

        }
    }

    private static void beginProduce(int i) {
        new Thread(() -> {
            for (; ; ) {
                produce();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p-" + i).start();
    }


    private static void beginConsume(int i) {
        new Thread(() -> {
            for (; ; ) {
                consume();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "c-" + i).start();
    }

    private static void produce() {
        try {
            LOCK.lock();
            while (TIMESTAMP_POOL.size() >= MAX_CAPCITY) {
                PRODECE_COND.await();
            }
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-P-" + value);
            TIMESTAMP_POOL.addLast(value);
            CONSUME_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }


    private static void consume() {
        try {
            LOCK.lock();
            while (TIMESTAMP_POOL.isEmpty()) {
                CONSUME_COND.await();
            }
            Long value = TIMESTAMP_POOL.removeFirst();
            System.out.println(Thread.currentThread().getName() + "-C-" + value);
            PRODECE_COND.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
