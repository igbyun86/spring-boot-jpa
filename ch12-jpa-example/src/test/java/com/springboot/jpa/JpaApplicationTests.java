package com.springboot.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    HandlerMethodArgumentResolver resolver;

    @Test
    void contextLoads() {
    }

}
