package io.pinest94.reactivestreams.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureEx {

    /***
     * 해당 클래스에서 알아야할 포인트
     * 1. future1,2를 실행하는 것과 job1,2를 실행하는 것의 차이
     * 2. future와 futureTask의 차이
     * 3. futureTask에서 done 메소드를 오버라이드한 익명 메소드내에서 get 메소드 호출이 시사하는 바를 알기
     * 4.
     */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<String> future = executorService.submit(() -> {
            Thread.sleep(3000);
            log.info("Async");
            return "Hello";
        });

        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            log.info("Async2");
            return "Hello2";
        }) {
            @Override
            protected void done() {
                try {
                    log.info(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        Long startTime = System.currentTimeMillis();

        executorService.execute(futureTask);
        log.info(future.get());
        // log.info(futureTask.get());
        // log.info(job());
        // log.info(job2());

        Long endTime = System.currentTimeMillis();

        log.info("elapsedTime : " + (endTime - startTime));
        executorService.shutdown();
    }

    public static String job() throws InterruptedException {
        Thread.sleep(2000);
        log.info("Async");
        return "Hello";
    }

    public static String job2() throws InterruptedException {
        Thread.sleep(2000);
        log.info("Async");
        return "Hello";
    }
}
