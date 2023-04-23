package com.ym.learn.auth.controller;

import com.ym.learn.auth.domain.Oauth2TokenDto;
import com.ym.learn.core.api.R;
import com.ym.learn.core.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 22:07
 * @Desc:
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public R<Oauth2TokenDto> getToken(Principal principal, @RequestParam Map<String, String> parameters){
        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
            Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                    .token(oAuth2AccessToken.getValue())
                    .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                    .expiresIn(oAuth2AccessToken.getExpiresIn())
                    .tokenHead(GlobalConstant.TOKEN_PREFIX)
                    .build();
            return R.ok(oauth2TokenDto);
        } catch (HttpRequestMethodNotSupportedException e) {
            log.error("AuthController.getToken(), error: "+e.getMessage());
            e.printStackTrace();
        }
        return R.fail("获取token失败");
    }
}
