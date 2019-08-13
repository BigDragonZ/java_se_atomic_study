package juc.utils.exchange;

import org.junit.Test;

import java.util.concurrent.Exchanger;

public class Exchange1 {

    @Test
    public void test() {
        final Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start");
            try {
                String result = exchanger.exchange("i am from A");
                System.out.println(Thread.currentThread().getName() + " get value" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" a end");
        }, "A").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "start");
            try {
                String result = exchanger.exchange("i am from b");
                System.out.println(Thread.currentThread().getName() + " get value" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" b end");
        }, "B").start();
    }
}
