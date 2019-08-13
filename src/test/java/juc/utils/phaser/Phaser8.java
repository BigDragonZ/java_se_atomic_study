package juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Phaser8 {
//    如何停止掉 Phaser
    public static void main(String[] args) throws InterruptedException {

        final Phaser phaser = new Phaser(3);

        new Thread(phaser::arriveAndAwaitAdvance).start();

        TimeUnit.SECONDS.sleep(3);

        System.out.println(phaser.isTerminated());

        phaser.forceTermination();
        System.out.println(phaser.isTerminated());

    }

}
