package juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * API讲解
 */
public class AtomicIntegerDetailTest {
    //    测试创建
    @Test
    public void testCreate() {
//        简单的get set 并不能体现它的优势
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.get());
        atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.get());
        atomicInteger.set(12);
        System.out.println(atomicInteger.get());
//        这个方法不常用:设置了 但是在你用的时候才去设置
        atomicInteger.lazySet(13);
        System.out.println(atomicInteger.get());
//        无锁：采用的是CPU级别的锁 不存在操作系统的锁
        AtomicInteger a = new AtomicInteger(12);
        int andSet = a.getAndAdd(10);
        System.out.println(andSet);
        System.out.println(a.get());
        System.out.println("********");
//        快速失败机制  compareAndSet  无限循环 对比获取的值和自己当前值是否改变
        AtomicInteger b = new AtomicInteger();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    int v = b.addAndGet(1);
                    System.out.println(v);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    int v = b.addAndGet(1);
                    System.out.println(v);
                }
            }
        }.start();
    }

    @Test
    public void test() {
        AtomicInteger a = new AtomicInteger(10);
        boolean b = a.compareAndSet(12, 20);
        System.out.println(b);
        System.out.println(a.get());
    }


    @Test
    public void test1() {
        AtomicInteger a = new AtomicInteger(1);
//        a.compareAndSet(2, 2);
//        System.out.println(a.get());

        a.accumulateAndGet(2, (c, b) -> c + b);
        System.out.println(a.get());

    }
}
