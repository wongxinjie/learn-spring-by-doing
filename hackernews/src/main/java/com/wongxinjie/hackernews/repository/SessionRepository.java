package com.wongxinjie.hackernews.repository;

import com.wongxinjie.hackernews.common.UUIDUtils;
import com.wongxinjie.hackernews.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class SessionRepository {

    private static final String prefix = "hackernews:session:";

    private long expiredSeconds = 3600;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    HashOperations<String, Object, Object> hashOperations;

    private String genStoreKey(String key) {
        return prefix + key;
    }

    public String create(User user) {
        String sessionId = "HN-" + UUIDUtils.uuid();
        String key = genStoreKey(sessionId);
        hashOperations.put(key, sessionId, user);
        redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
        return sessionId;
    }

    public User getUserFromSession(String sessionId) {
        String key = genStoreKey(sessionId);

        User user = (User) hashOperations.get(key, sessionId);
        return user;
    }

    public boolean remove(String sessionId) {
        String key = genStoreKey(sessionId);
        return redisTemplate.delete(key);
    }
}
