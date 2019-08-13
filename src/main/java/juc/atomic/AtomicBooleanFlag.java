package juc.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanFlag
{
    private final static AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                while (flag.get()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(" i am working");
                }
            }
        }.start();

        Thread.sleep(50000);
        flag.set(false);
    }
}
