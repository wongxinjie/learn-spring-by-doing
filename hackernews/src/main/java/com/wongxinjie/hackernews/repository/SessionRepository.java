package com.wongxinjie.hackernews.repository;

import com.wongxinjie.hackernews.common.UUIDUtils;
import com.wongxinjie.hackernews.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SessionRepository {

    private static final String prefix = "hackernews:session:";

    private long expiredSeconds = 3600;

    @Autowired
    RedisTemplate<String, User> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<String, User> valueOperations;

    private String genStoreKey(String key) {
        return prefix + key;
    }

    public String create(User user) {
        String sessionId = "HN-" + UUIDUtils.uuid();
        String key = genStoreKey(sessionId);
        valueOperations.set(key, user, expiredSeconds);
        return sessionId;
    }

    public User getUserFromSession(String sessionId) {
        String key = genStoreKey(sessionId);
        User user = valueOperations.get(key);
        return user;
    }

    public boolean remove(String sessionId) {
        String key = genStoreKey(sessionId);
        return redisTemplate.delete(key);
    }
}
