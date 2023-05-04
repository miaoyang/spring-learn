package com.ym.learn.gateway.authorization;

import cn.hutool.core.convert.Convert;
import com.ym.learn.core.constant.RedisConstant;
import com.ym.learn.gateway.constant.AuthConstant;
import com.ym.learn.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 16:38
 * @Desc: 鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private RedisService redisService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        URI uri = object.getExchange().getRequest().getURI();

        Object obj = redisService.hGet(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        authorities.stream().map(i->
            i = AuthConstant.AUTHORITY_PREFIX+i
        ).collect(Collectors.toList());

        return authentication.filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
