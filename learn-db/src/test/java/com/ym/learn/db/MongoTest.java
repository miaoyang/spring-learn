package com.ym.learn.db;

import com.ym.learn.db.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/19 22:17
 * @Desc:
 */
@SpringBootTest(classes = MongoTest.class)
@Slf4j
class MongoTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void setUser(){
        User user = User.builder()
                .name("yangmiao")
                .age(10)
                .password("123")
                .build();
        mongoTemplate.insert(user);
    }

    @Test
    void getUser(){
        List<User> all = mongoTemplate.findAll(User.class);
        log.debug(all.toString());
    }
}
