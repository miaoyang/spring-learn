package com.ym.learn.db.repository.mapper.mongo;

import com.ym.learn.db.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/20 19:15
 * @Desc:
 */
public interface UserMapper extends MongoRepository<User,String> {

}
