package io.pinest.springbatchdemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // 이 애플리케이션은 배치프로세스를 사용한다는 어노테이션
public class SpringbatchdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbatchdemoApplication.class, args);
    }

}
