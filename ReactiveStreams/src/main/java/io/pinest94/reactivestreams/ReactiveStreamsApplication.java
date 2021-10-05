package io.pinest94.reactivestreams;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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
