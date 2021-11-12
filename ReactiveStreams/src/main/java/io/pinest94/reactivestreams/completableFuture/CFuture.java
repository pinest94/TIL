package io.pinest94.reactivestreams.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture
                .supplyAsync(() -> {
                    log.info("runAsync");
                    return 1;
                }, es)
                .thenApply(s -> {
                    log.info("then Apply {}", s);
                    return s + 1;
                })
                .thenApplyAsync(s2 -> {
                    log.info("then Apply {}", s2);
                    return s2 * 3;
                }, es)
                .exceptionally(e -> -10)
                .thenAcceptAsync(s3 -> log.info("then Accept {}", s3), es);
        log.info("exit");
        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
