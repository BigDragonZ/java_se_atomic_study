package juc.atomic;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决了ABA问题 内部封装了一个对
 * 类似于数据库的乐观锁
 * 加戳：内部封装了一个对  integer和reference
 * 在进行比对时判断 reference和integer是否一致
 */
public class AtomicStamperReferenceTest {

    private static AtomicStampedReference<Integer> atomicRef = new AtomicStampedReference<>(100, 0);

    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    boolean b1 = atomicRef.compareAndSet(100, 101, atomicRef.getStamp(), atomicRef.getStamp() + 1);
                    System.out.println("b1" + b1);
                    boolean b2 = atomicRef.compareAndSet(101, 100, atomicRef.getStamp(), atomicRef.getStamp() + 1);
                    System.out.println("b2" + b2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    int stamp = atomicRef.getStamp();
                    System.out.println("before sleep: stamp" + stamp);
                    TimeUnit.SECONDS.sleep(2);
                    boolean b = atomicRef.compareAndSet(100, 101, stamp, stamp + 1);
                    System.out.println("b" + b);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
