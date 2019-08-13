package juc.utils.forkjoin;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class RecursiveTask1 {

    private final static int MAX_THRESHOLD = 3;

    // 分而治之 有返回值的
    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> future = forkJoinPool.submit(new CalculatedRecursiveTask(0, 10));
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class CalculatedRecursiveTask extends RecursiveTask<Integer> {

        private final int start;

        private final int end;

        public CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= MAX_THRESHOLD) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (start + end) / 2;
                CalculatedRecursiveTask left = new CalculatedRecursiveTask(start, middle);
                CalculatedRecursiveTask right = new CalculatedRecursiveTask(middle + 1, end);
                left.fork();
                right.fork();
                return left.join() + right.join();
            }
        }
    }
}
