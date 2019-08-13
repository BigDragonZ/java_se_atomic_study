package juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Phaser7 {
//    public static void main(String[] args) throws InterruptedException {
////        不可以被打断 打断方法不起作用
//        final Phaser phaser = new Phaser(3);
//        Thread thread = new Thread(phaser::arriveAndAwaitAdvance);
//        thread.start();
//        System.out.println("=======");
//        TimeUnit.SECONDS.sleep(10);
//
//        thread.interrupt();
//
//        System.out.println("++++interrupt ");
//    }


//

    public static void main(String[] args) throws InterruptedException {
//        final Phaser phaser = new Phaser(3);
//        Thread thread = new Thread(()->{
//            try {
////                该方法可以被打断  具体看javadoc
//                phaser.awaitAdvanceInterruptibly(phaser.getPhase());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        thread.start();
//        System.out.println("=======");
//        TimeUnit.SECONDS.sleep(10);
//
//        thread.interrupt();
//
//        System.out.println("++++interrupt ");

        final Phaser phaser = new Phaser(3);
        Thread thread = new Thread(() -> {
            try {
//                该方法可以被打断  具体看javadoc
                phaser.awaitAdvanceInterruptibly(0, 10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("=======");
        TimeUnit.SECONDS.sleep(10);

        thread.interrupt();

        System.out.println("++++interrupt ");
    }
}
