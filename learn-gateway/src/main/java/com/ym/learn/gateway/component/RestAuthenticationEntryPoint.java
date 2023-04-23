package com.ym.learn.gateway.component;

import cn.hutool.json.JSONUtil;
import com.ym.learn.core.api.R;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 20:45
 * @Desc: 没有登录或者token过期
 */
@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");

        String jsonStr = JSONUtil.toJsonStr(R.unauthorized(ex.getMessage()));
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonStr.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(dataBuffer));
    }
}