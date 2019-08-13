package juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
// 可以重复使用
public class Phaser2 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 6; i++) {
            new Athletes(i, phaser).start();
        }
    }

    static class Athletes extends Thread {

        private final int num;
        private final Phaser phaser;

        public Athletes(int num, Phaser phaser) {
            this.num = num;
            this.phaser = phaser;
        }

        @Override
        public void run() {

            try {
                System.out.println(num + " start running");
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(num + " end running");
                phaser.arriveAndAwaitAdvance();
                System.out.println(num + " start bicycle");
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(num + " end bicycle");
                phaser.arriveAndAwaitAdvance();
                System.out.println(num + " start long jump");
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(num + " end jump");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
