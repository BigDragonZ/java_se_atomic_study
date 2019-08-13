package juc.utils.phaser;

import java.nio.file.Paths;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Phaser6 {

    public static void main(String[] args) throws InterruptedException {
//         final Phaser phaser = new Phaser(6);
////        不参与 arrived的计算
//        new Thread(()->phaser.awaitAdvance(0)).start();
//
//        TimeUnit.SECONDS.sleep(3);
//        System.out.println(phaser.getArrivedParties());


        final Phaser phaser = new Phaser(6);
        IntStream.rangeClosed(0, 5).boxed().map(i -> phaser).forEach(AwaitAdvanceTask::new);
//        负责监控其余的线程 等其他的线程全部完成后取消阻塞 执行后续任务
        phaser.awaitAdvance(phaser.getPhase());

        System.out.println("=========");
    }


    private static class AwaitAdvanceTask extends Thread {
        private final Phaser phaser;

        public AwaitAdvanceTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + " finished work");
        }
    }
}
