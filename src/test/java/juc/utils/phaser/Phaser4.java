package juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Phaser4 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(1);
        new OnAdvanceTask("alex", phaser).start();
        new OnAdvanceTask("jack", phaser).start();
    }

    static class OnAdvanceTask extends Thread {
        private final Phaser phaser;


        public OnAdvanceTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + "i am start");
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + " i am end");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
