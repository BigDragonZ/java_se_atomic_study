package juc.utils.executors;

import java.util.Timer;
import java.util.TimerTask;

public class TimerScheduler {
    /**
     * 定时任务
     * Timer/TimerTask    jdk1.3出现 现在不使用了
     * SchdulerExecutorService
     * crontab
     * cron4j
     * quartz
     *
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("=====" + System.currentTimeMillis());
            }
        };

        timer.schedule(task, 1000);
    }
}
