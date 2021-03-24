package com.pinest94.jpa.bookmanager.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void test() {
        User user = new User();
        user.setEmail("pinest94@gmail.com");
        user.setName("pinest94");

        User user2 = User.builder()
                .name("hansol")
                .email("hansol@gmail.com")
                .build();


        System.out.println(">>> " + user);
        System.out.println(">>> " + user2);
    }

}