package com.ym.learn.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/19 22:19
 * @Desc: User实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "t_user")
public class User implements Serializable {

    private String name;

    private String password;

    private int age;

    private String tel;

}
