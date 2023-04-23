package com.ym.learn.gateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import com.ym.learn.core.constant.GlobalConstant;
import com.ym.learn.gateway.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;


/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 16:08
 * @Desc: 白名单路径访问时需要移除JWT请求头
 */
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> urls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl:urls){
            if (antPathMatcher.match(ignoreUrl,uri.getPath())){
                request = exchange.getRequest().mutate().header(GlobalConstant.AUTHORIZATION,"").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
