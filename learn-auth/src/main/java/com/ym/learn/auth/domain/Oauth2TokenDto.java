package com.ym.learn.auth.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 21:55
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Oauth2TokenDto {
//    @ApiModelProperty("访问令牌")
    private String token;

//    @ApiModelProperty("刷令牌")
    private String refreshToken;

//    @ApiModelProperty("访问令牌头前缀")
    private String tokenHead;

//    @ApiModelProperty("有效时间（秒）")
    private int expiresIn;
}
