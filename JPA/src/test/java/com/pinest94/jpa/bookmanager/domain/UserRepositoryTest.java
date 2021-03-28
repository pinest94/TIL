package com.pinest94.jpa.bookmanager.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    // @Transactional // 세션을 해당 함수에 유지시켜줌
    void crudTest() {

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

        // User user = userRepository.findById(1L).orElse(null);
        // System.out.println(user);

        // 4. delete
        // delete메서드에 파라미터로 null값이 들어갈 수 없다.(중요)
        // userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));
        // userRepository.deleteById(1L);
        
        // delete와 deleteInBatch의 차이점
        // delete의 경우 select를 통해 해당 엔티티가 있는 지 한번 더 확인한다.
        // 두번 째는 delete할 개수만큼 for-loop를 이용하여 삭제한다 (ex. 1000만개 row삭제 시 1000만회 쿼리실행)
        // deleteInBatch는 엔티티가 있는지 없는지 확인하지 않고 여러개의 row도 하나의 쿼리로 삭제하므로 훨씬 성능이 좋다.
        // userRepository.deleteAll(userRepository.findAllById(Lists.newArrayList(1L, 3L)));
        userRepository.deleteAllInBatch();
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void updateTest() {
        userRepository.save(User.builder().name("kris").email("kris@gmail.com").build());

        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setEmail("kris@naver.com");
        userRepository.save(user);
    }

    @Test
    public void pageTest() {
        Page<User> users = userRepository.findAll(PageRequest.of(1, 5));

        System.out.println("page : " + users);
        System.out.println("totalElements : " + users.getTotalElements());
        System.out.println("totalPage : " + users.getTotalPages());
        System.out.println("numberOfElements : " + users.getNumberOfElements());
        System.out.println("sort : " + users.getSort());
        System.out.println("size : " + users.getSize());

        users.getContent().forEach(System.out::print);
    }

    // QBE : Query By Example
    @Test
    public void QBETest() {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("name")
                .withMatcher("email", endsWith());

        Example<User> example = Example.of(new User("ma", "pinest.com"), exampleMatcher);

        userRepository.findAll(example).forEach(System.out::println);

    }
}