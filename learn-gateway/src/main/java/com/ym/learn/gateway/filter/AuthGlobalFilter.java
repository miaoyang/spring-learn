package com.ym.learn.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import com.ym.learn.core.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 15:53
 * @Desc: 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(GlobalConstant.AUTHORIZATION);
        if (StrUtil.isEmpty(token)){
            return chain.filter(exchange);
        }
        try {
            // 去掉前缀
            String realToken = token.replace(GlobalConstant.TOKEN_PREFIX,"");
            JWSObject parse = JWSObject.parse(realToken);
            String userStr = parse.getPayload().toString();
            log.debug("AuthGlobalFilter.filter() user: "+userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header(GlobalConstant.HEADER_USER, userStr).build();
            exchange = exchange.mutate().request(request).build();

        }catch (Exception e){
            log.error("error: "+e.getMessage());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
