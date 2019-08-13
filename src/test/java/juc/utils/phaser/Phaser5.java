package juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Phaser5 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
//        final Phaser phaser = new Phaser(3);

//等待其他线程到达，直到数值减到0 再进行统一放行
//        new Thread(phaser::arriveAndAwaitAdvance).start();
//        arrive 到达后不需要等待其他线程，可以继续执行
//        new Thread(phaser::arrive).start();


        final Phaser phaser = new Phaser(5);

        for (int i = 0; i < 4; i++) {
            new ArriveTask(i, phaser).start();
        }

        phaser.arriveAndAwaitAdvance();

        System.out.println(" the phase 1 work finished done");
    }


    private static class ArriveTask extends Thread {
        private final Phaser phaser;

        public ArriveTask(int no, Phaser phaser) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + "start working");
            Phaser5.sleep();
            System.out.println(getName() + "the phase one is running");
            phaser.arrive();

            Phaser5.sleep();

            System.out.println(getName() + "keep to do other things");

        }
    }


    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
