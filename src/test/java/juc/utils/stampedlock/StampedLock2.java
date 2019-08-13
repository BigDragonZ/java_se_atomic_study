package juc.utils.stampedlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

public class StampedLock2 {
    /**
     * StampedLock为什么出现：
     * sync:  不够灵活
     * ReentrantLock ： 无法解决读写问题 不支持同时读
     * ReentrantReadWriteLock：解决了读写问题 可以同时读，但是如果100个线程  99个读 1个写 此时该锁的性能不高
     * StampedLock解决了上面的问题，锁一步步的加强
     */


    private final static StampedLock lock = new StampedLock();

    private final static List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (; ; ) {
                read();
            }
        };

        Runnable writeTask = () -> {
            for (; ; ) {
                write();
            }
        };
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(readTask);
        executorService.submit(writeTask);
    }

    private static void read() {
// 乐观锁
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            try {
                stamp = lock.readLock();
//            悲观式的，读的时候不允许写锁
                Optional.of(data.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))).ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    private static void write() {
        long stamped = -1;
        try {
            stamped = lock.writeLock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }
}
