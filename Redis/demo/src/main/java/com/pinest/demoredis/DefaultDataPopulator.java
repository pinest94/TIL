package com.pinest.demoredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultDataPopulator implements ApplicationRunner {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setProduct("나이키 신발");
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);

        transactionRepository.findAll().forEach(t-> {
            System.out.println("=======================");
            System.out.println(t.getProduct() + " " + t.getCreatedAt());
        });
    }
}
