package juc.atomic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子操作類
 */
public class AtomicIntegerTest {

    /**
     * 关键字  功能：可见性 内存屏障（顺序性） 但是不能保证原子
     */
//    private static volatile int value = 0;
    // 三个线程会出现重复数据
    private static Set<Integer> set = Collections.synchronizedSet(new HashSet<>());

    //    未使用AtomiInteger时会出现重复数据现象
    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread() {
//            @Override
//            public void run() {
//                int x = 0;
//                while (x < 500) {
//                    set.add(value);
//                    int tmp = value;
//                    System.out.println(Thread.currentThread().getName() + " " + tmp);
//                    value += 1;
//                    /**
//                     * 从内存中取出value的值
//                     * value+1
//                     * 将计算后结果付给value
//                     *  刷新回内存中
//                     */
//                    x++;
//                }
//            }
//        };
//        Thread t2 = new Thread() {
//            @Override
//            public void run() {
//                int x = 0;
//                while (x < 500) {
//                    set.add(value);
//                    int tmp = value;
//                    System.out.println(Thread.currentThread().getName() + " " + tmp);
//                    value += 1;
//                    x++;
//                }
//            }
//        };
//        Thread t3 = new Thread() {
//            @Override
//            public void run() {
//                int x = 0;
//                while (x < 500) {
//                    set.add(value);
//                    int tmp = value;
//                    System.out.println(Thread.currentThread().getName() + " " + tmp);
//                    value += 1;
//                    x++;
//                }
//            }
//        };
//        t1.start();
//        t2.start();
//        t3.start();
//        t1.join();
//        t2.join();
//        t3.join();
//        System.out.println(set.size());

        /**
         *
         *
         * 原子类 解决了 多线程中 原子 有序 可见性
         *
         *
         *
         */
//        1.7后自动设置为final类型
        final AtomicInteger value = new AtomicInteger();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                int x = 0;
                while (x < 500) {
                    int v = value.getAndDecrement();
                    set.add(v);
                    System.out.println(Thread.currentThread().getName() + " " + v);
                    x++;
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                int x = 0;
                while (x < 500) {
                    int v = value.getAndDecrement();
                    set.add(v);
                    System.out.println(Thread.currentThread().getName() + " " + v);
                    x++;
                }
            }
        };
        Thread t3 = new Thread() {
            @Override
            public void run() {
                int x = 0;
                while (x < 500) {
                    int v = value.getAndDecrement();
                    set.add(v);
                    System.out.println(Thread.currentThread().getName() + " " + v);
                    x++;
                }
            }
        };
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(set.size());
    }



}
