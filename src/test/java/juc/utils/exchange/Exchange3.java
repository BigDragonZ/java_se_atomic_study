package juc.utils.exchange;

import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Exchange3 {

    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            AtomicReference<Integer> value = new AtomicReference<>(1);
            try {
                while (true){
                    value.set(exchanger.exchange(value.get()));
                    System.out.println("a has value" + value);
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            AtomicReference<Integer> value = new AtomicReference<>(2);
            try {
                while (true){
                    value.set(exchanger.exchange(value.get()));
                    System.out.println("b has value" + value);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
    @Test
    public void test1() {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            AtomicReference<Integer> value = new AtomicReference<>(1);
            try {
                while (true){
                    value.set(exchanger.exchange(value.get()));
                    System.out.println("a has value" + value);
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            AtomicReference<Integer> value = new AtomicReference<>(1);
            try {
                while (true){
                    value.set(exchanger.exchange(value.get()));
                    System.out.println("b has value" + value);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }
}
