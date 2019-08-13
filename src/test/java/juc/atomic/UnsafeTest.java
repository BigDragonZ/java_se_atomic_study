package juc.atomic;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnsafeTest {
    @Test
    public void test() {
//        Unsafe unsafe = Unsafe.getUnsafe();
//        System.out.println(unsafe);

        System.out.println(getUnsafe());
    }

    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe o = (Unsafe) f.get(null);
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    interface Counter {
        void increment();

        long getCounter();
    }

    static class CounterRunnble implements Runnable {
        private final Counter counter;

        private final int num;

        public CounterRunnble(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }


    static class StupidCounter implements Counter {

        private long counter = 0;

        @Override
        public void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class SyncCounter implements Counter {

        private long counter = 0;

        @Override
        public synchronized void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }


    static class LockCounter implements Counter {
        private final Lock lock = new ReentrantLock();
        private long counter = 0;

        @Override
        public void increment() {
            try {
                lock.lock();
                counter++;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class AtomicCounter implements Counter {

        private AtomicLong counter = new AtomicLong();

        @Override
        public synchronized void increment() {
            counter.incrementAndGet();
        }

        @Override
        public long getCounter() {
            return counter.get();
        }
    }


    static class CASCounter implements Counter {

        private volatile long counter = 0;
        private Unsafe unsafe;
        private long offSet;

        public CASCounter() throws Exception {
            unsafe = getUnsafe();
            unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        }

        @Override
        public synchronized void increment() {
            long current = counter;
            while (!unsafe.compareAndSwapLong(this,offSet,current,current+1)){
                current = counter;
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    @Test
    public void test2() throws InterruptedException {
        /**
         * StupidCounter 结果不对
         * counter result9842266
         * time pass222
         */
        ExecutorService service = Executors.newFixedThreadPool(1000);
        Counter counter = new StupidCounter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnble(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("counter result" + counter.getCounter());
        System.out.println("time pass" + (end - start));
    }

    @Test
    public void test3() throws InterruptedException {
        /**
         *
         * SyncCounter  结果是对的
         * counter result10000000
         * time pass736
         */
        ExecutorService service = Executors.newFixedThreadPool(1000);
        Counter counter = new SyncCounter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnble(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("counter result" + counter.getCounter());
        System.out.println("time pass" + (end - start));
    }

    @Test
    public void test4() throws InterruptedException {
        /**
         *
         * LockCounter  结果是对的
         counter result10000000
         time pass386
         */
        ExecutorService service = Executors.newFixedThreadPool(1000);
        Counter counter = new LockCounter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnble(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("counter result" + counter.getCounter());
        System.out.println("time pass" + (end - start));
    }

    @Test
    public void test5() throws InterruptedException {
        /**
         *
         * LockCounter  结果是对的
         counter result10000000
         time pass386
         */
        ExecutorService service = Executors.newFixedThreadPool(1000);
        Counter counter = new AtomicCounter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnble(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("counter result" + counter.getCounter());
        System.out.println("time pass" + (end - start));
    }

    @Test
    public void test6() throws Exception {
        /**
         *
         * LockCounter  结果是对的
         counter result10000000
         time pass386
         */
        ExecutorService service = Executors.newFixedThreadPool(1000);
        Counter counter = new CASCounter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnble(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("counter result" + counter.getCounter());
        System.out.println("time pass" + (end - start));
    }
}
