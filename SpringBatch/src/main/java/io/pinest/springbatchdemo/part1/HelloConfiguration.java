package io.pinest.springbatchdemo.part1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class HelloConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    // Spring Batch 설정에 의해서 이미 빈으로 생성되어 있기 때문에 생성자 주입이 가능하다
    public HelloConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    /**
     * Job : Spring Batch의 실행단위
     * incrementer : 실행단위를 구분
     * RunIdIncrementer : job이 실행할 때마다 파라미터 아이디를 자동으로 생성해 줌
     * get("이름") : get메서드에 [이름]을 넣을 수 있고 [이름]은 스프링 배치를 실행할 수 있는 키이다.
     * start : job 실행시 최초로 실행될 step을 설정할 메서드
     * */
    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer())
                .start(this.helloStep())
                .build();
    }

    /**
     * step은 job의 실행단위
     * 하나의 job은 여러 개의 step을 가질 수 있다
     * step에는 task기반과 chuck기반이 있다
     * */
    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloStep")
                .tasklet((contribution, chunkContext) -> {
                  log.info("hello spring batch");
                  return RepeatStatus.FINISHED;
                }).build();
    }
}
