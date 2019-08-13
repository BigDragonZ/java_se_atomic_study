package juc.utils.executors;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Executors2 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newWorkStealingPool();
        Callable<String> callable = () -> {
            return "task -1";
        };

        List<Callable<String>> callList = IntStream.rangeClosed(0, 20).boxed().map(i ->
                (Callable<String>) () -> {
                    sleep(100);
                    return "task" + i;
                }
        ).collect(Collectors.toList());
        executorService.invokeAll(callList);
//        Optional.of(Runtime.getRuntime().availableProcessors()).ifPresent(System.out::println);
    }


    private static void sleep(long s) {
        try {
            TimeUnit.SECONDS.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
