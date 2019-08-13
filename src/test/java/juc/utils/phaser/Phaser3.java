package juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

// 可以重复使用
public class Phaser3 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 1; i < 5; i++) {
            new Athletes(i, phaser).start();
        }
        new InjuredAthletes(5, phaser);
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
                sport(num, phaser, " start running", " end running");
                sport(num, phaser, " start bicycle", " end bicycle");
                sport(num, phaser, " start long jump", " end long jump");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class InjuredAthletes extends Thread {

        private final int num;
        private final Phaser phaser;

        public InjuredAthletes(int num, Phaser phaser) {
            this.num = num;
            this.phaser = phaser;
        }

        @Override
        public void run() {

            try {
                sport(num, phaser, " start running", " end running");
                sport(num, phaser, " start bicycle", " end bicycle");
//                sport(num, phaser, " start long jump", " end long jump");

                System.out.println("oh shit i am injured");
//                程序出现异常时取消注册
                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sport(int num, Phaser phaser, String s, String s2) throws InterruptedException {
        System.out.println(num + s);
        TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
        System.out.println(num + s2);
        phaser.arriveAndAwaitAdvance();
    }

}
