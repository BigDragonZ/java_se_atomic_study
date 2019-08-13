package juc.utils.exchange;

import org.junit.Test;

import java.util.concurrent.Exchanger;

public class Exchange2 {

    /**
     * a和b发送的是同一个对象，此时会出现线程安全问题。
     */
    @Test
    public void test1(){
        final Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(()->{
           Object o = new Object();
            System.out.println(" a will send the object" + o);
            try {
                Object r = exchanger.exchange(o);
                System.out.println("a rec the object" + r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()->{
            Object o = new Object();
            System.out.println(" b will send the object" + o);
            try {
                Object r = exchanger.exchange(o);
                System.out.println("b rec the object" + r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
