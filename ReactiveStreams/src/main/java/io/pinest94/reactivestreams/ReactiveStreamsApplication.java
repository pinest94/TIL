package io.pinest94.reactivestreams;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAsync
public class ReactiveStreamsApplication {

    /***
     * 해당 클래스에서 알아야할 포인트
     * 1. run 메소드가 어떻게 동작할지 생각하기
     * 2. 실제 서비스에서는 @Async만 절대 사용하지 않는다. 이유는 @Async는 요청이 들어오면 새로운 스레드를 생성하지만 스레드풀로 관리하거나 캐시가 따로 존재하지 않는다.
     *    즉 요청이 들어오면 계속 생성을 하기 때문에 메모리와 CPU에 상당한 부담을 주게된다.
     * 3. 실제 서비스에서는 @Bean으로 ThreadPoolTaskExecutor를 만들어서 Thread를 관리하여 @Async를 사용하도록 한다.
     * 4. @Bean으로 정의된 ThreadPoolTaskExecutor가 있다면 @Async는 디폴트로 빈에 정의된 것을 사용한다.
     * 5. 각 메서드마다 스레드 풀 정책을 달리하고 싶어서 정의된 것이 여러 개인 경우에는 @Async(value = "tp")으로 사용하면 된다.
     */

    @RestController
    public static class MyController {

        Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();
        static String description = "Hi! My name is hansol kim";

        @GetMapping("/callable")
        public Callable<String> callable() throws InterruptedException {
            log.info("callable");
            return () -> {
                log.info("async");
                Thread.sleep(5000);
                return "hello";
            };
        }

        @GetMapping("/dr")
        public DeferredResult<String> deferred() {
            log.info("dr");
            DeferredResult<String> dr = new DeferredResult<>();
            results.add(dr);
            return dr;
        }

        @GetMapping("/dr/count")
        public String drcount() {
            return String.valueOf(results.size());
        }

        @GetMapping("/dr/event")
        public String drevent(String msg) {
            for(DeferredResult<String> dr : results) {
                dr.setResult("Hello " + msg);
                results.remove(dr);
            }
            return "OK";
        }

        @GetMapping("/async")
        public String async() throws InterruptedException {
            log.info("async");
            Thread.sleep(5000);
            return "hello";
        }

        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter() {
            ResponseBodyEmitter emitter = new ResponseBodyEmitter();

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    emitter.send("<p>");
                    for(int i=0; i<=description.length(); ++i) {
                        emitter.send(description.charAt(i));
                        Thread.sleep(100);
                    }
                    emitter.send("</p>");
                } catch (Exception e) {}
            });

            return emitter;
        }
    }

    @Component
    public static class MyService {
        @Async
        public Future<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(1000L);
            return new AsyncResult<>("hello");
        }
    }

    @Bean
    ThreadPoolTaskExecutor tp() {
        /***
         * setCorePoolSize, setMaxPoolSize, setQueueCapacity를 알아보자
         * 기본적으로 스레드의 풀사이즈는 setCorePoolSize의 설정 값을 따른다. 여기까지는 어렵지 않다.
         * 만약 10개의 스레드가 모두 사용 중이고 11번째 스레드 사용 요청이 들어오면 어떻게 될까?
         * 정답은 setQueueCapacity으로 설정된 큐에 해당 요청을 대기시킨다.
         * 그래서 해당 큐가 모두 꽉차게 될때(=setQueueCapacity) 스레드를 증가시키고 최대 setMaxPoolSize값만큼 증가시킨다.
         * 주의❗ 11번째 스레드 요청이 들어올 때 setMaxPoolSize값 만큼 스레드가 증가하는 것이 아니다.
         */
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(200);
        threadPoolTaskExecutor.setThreadNamePrefix("rx : ");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveStreamsApplication.class, args);
    }

    @Autowired
    MyService myService;

    @Bean
    ApplicationRunner run() {
        return args -> {
            log.info("run()");
            Future<String> future = myService.hello();
            log.info("result : " + future.isDone());
            log.info("result : " + future.get());
        };
    }

}
