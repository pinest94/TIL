package io.pinest94.reactivestreams.futures;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService executorService = Executors.newFixedThreadPool(100 );

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest";

        CyclicBarrier barrier = new CyclicBarrier(101);

        StopWatch main = new StopWatch();
        main.start();

        for(int i=0; i<100; i++) {
            executorService.submit(() -> {
                int idx = counter.addAndGet(1);

                barrier.await(); // 스레드가 100개 만들어질때까지 기다림

                log.info("Thread : " + idx);

                StopWatch sw = new StopWatch();
                sw.start();

                String res = restTemplate.getForObject(url+"/"+idx, String.class);

                sw.stop();
                log.info("Elapsed Time : {}, {} / {}", idx, sw.getTotalTimeSeconds(), res);
                return null;
            });
        }

        barrier.await();
        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("Total : {}", main.getTotalTimeSeconds());
    }
}
