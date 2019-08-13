package juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// 可以动态增加
public class Phaser1 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
//        兼容了CountDownLatch和CyclicBarried的优点
//        计数可以重置
//        运行过程中可以动态注册，其他两个是创建时就指定好的。
        final Phaser phaser = new Phaser();
        IntStream.rangeClosed(0, 5).boxed().map(i -> phaser).forEach(Task::new);
        phaser.register();
        phaser.arriveAndAwaitAdvance();

        System.out.println("all of worked  finished");
    }


    static class Task extends Thread {
        private final Phaser phaser;

        public Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
        }

        @Override
        public void run() {
            System.out.println("the worker [" + Thread.currentThread().getName() + "] is working");
            try {
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
