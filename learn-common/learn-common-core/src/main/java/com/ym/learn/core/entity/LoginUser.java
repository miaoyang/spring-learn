package com.ym.learn.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yangmiao
 * @Date: 2023/3/21 11:08
 * @Desc:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String name;

    private String password;

    private int age;

    private String tel;
}
