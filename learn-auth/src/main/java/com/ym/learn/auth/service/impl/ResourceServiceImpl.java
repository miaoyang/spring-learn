package com.ym.learn.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ym.learn.core.constant.RedisConstant;
import com.ym.learn.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Yangmiao
 * @Date: 2023/4/9 22:38
 * @Desc: 资源服务，把资源与角色匹配关系缓存到Redis中
 */
@Service
public class ResourceServiceImpl {

    private Map<String, List<String>> resourceRolesMap;

    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/api/hello", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/api/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
        // 放进缓存
        redisService.hSetAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
