package com.ym.learn.db.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.ym.learn.core.api.R;
import com.ym.learn.db.entity.User;
import com.ym.learn.db.repository.mapper.mongo.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/20 17:40
 * @Desc:
 */
@RestController("/mongo")
@Slf4j
public class MongoController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/addUser")
    public R addUser(){
        User user = User.builder()
                .name("xiaoming")
                .age(10)
                .password("123")
                .build();
        User insert = mongoTemplate.insert(user);
        return R.ok(insert);
    }

    @GetMapping("/deleteUser")
    public R deleteUser(){
        Query query = Query.query(Criteria.where("name").is("lisi").and("age").is(10));
        DeleteResult remove = mongoTemplate.remove(query);
        return R.ok(remove);
    }

    @GetMapping("/updateUser")
    public R updateUser(){
        Query query = Query.query(Criteria.where("name").is("xiaoming"));
        Update update = Update.update("name", "lisi");
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        return R.ok(result);
    }

    @GetMapping("queryUser/{name}")
    public R queryUser(@PathVariable("name")String name){
        Query query = Query.query(Criteria.where("name").is(name));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null){
            return R.fail("未查询到该用户:"+name);
        }
        return R.ok(user);
    }

    @GetMapping("/queryUser/repos/{name}")
    public R queryUserByRepos(@PathVariable("name")String name){
        Optional<User> userOptional = userMapper.findById(name);
        userOptional.ifPresent(it->{
            log.debug("print user: "+it.toString());
        });
        User user = userOptional.orElse(new User());
        return R.ok(user);
    }
}
