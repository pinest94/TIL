package com.pinest94.jpa.bookmanager.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    // @Transactional // 세션을 해당 함수에 유지시켜줌
    void createTest() {

        // 1. create
        User user1 = User.builder().name("jack").email("jack@pinest.com").build();
        User user2 = User.builder().name("jack").email("jack@pinest.com").build();
        userRepository.saveAll(Lists.newArrayList(user1, user2));
        
        // userRepository.findAll().forEach(System.out::println);

        /**
         * 위의 코드를 Java 8 형식이 아닌 코드로 작성한다면?
         * ↓ ↓ ↓ ↓ ↓ ↓
         * */
//        for(User user : userRepository.findAll()) {
//            System.out.println(user);
//        }
        
        // 2. read
        
        // 전체조회 테스트
        // userRepository.findAll(Sort.by(Sort.Direction.DESC, "name")).forEach(System.out::println);
        
        // 일부 조회
        // userRepository.findAllById(Lists.newArrayList(1L, 3L, 5L)).forEach(System.out::println);

        // getOne과 findById의 차이
        // getOne은 Entity를 반환하고 findById는 Optional(한번 더 맵핑)을 반환한다
        // 역할은 동일하나 실제 동작은 다름
        // getOne은 Lazy로딩방식을 사용하기 때문에 세션 유지가 안되면 print시 오류를 발생한다.

        // User user = userRepository.getOne(1L);

        User user = userRepository.findById(1L).orElse(null);
        System.out.println(user);
    }
}