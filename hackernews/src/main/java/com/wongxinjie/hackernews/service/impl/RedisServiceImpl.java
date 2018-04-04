package com.wongxinjie.hackernews.service.impl;

import com.wongxinjie.hackernews.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class RedisServiceImpl<T> implements RedisService<T> {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }


    public <T> ValueOperations<String, T> setCacheObjectWithExpire(String key, T value, long seconds) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, seconds, TimeUnit.SECONDS);
        return operation;
    }



    /**
     * 缓存List数据
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add((T) listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);

        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     */
    public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);

        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {

        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {

            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }


    /**
     * 缓存Map
     */
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     */
    public <T> Map<Integer, T> getCacheIntegerMap(String key) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }


    @Override
    public boolean remove(String redisKey) {
        try {
            redisTemplate.delete(redisKey);
            logger.debug("succeeded to delete redis key {}", redisKey);
            return true;
        } catch (Exception e) {
            logger.error("failed to delete redis key {}", redisKey, e);
        }
        return false;
    }

    @Override
    public boolean containsKey(String redisKey) {
        try {
            return redisTemplate.hasKey(redisKey);
        } catch (Exception e) {
            logger.error("failed to query redis key {}", redisKey, e);
        }
        return false;
    }

    @Override
    public boolean setObject(String redisKey, T value) {
        try {
            setCacheObject(redisKey, value);
            return true;
        } catch (Exception e) {
            logger.error("failed to set redis key {}", redisKey, e);
        }
        return false;
    }

    @Override
    public <T> T getObject(String redisKey) {
        try {
            T operation = getCacheObject(redisKey);
            return operation;
        } catch (Exception e) {
            logger.error("failed to query redis key {}", redisKey, e);
        }
        return null;
    }

    @Override
    public boolean setexObject(String redisKey, T value, long seconds) {
        try {
            setCacheObjectWithExpire(redisKey, value, seconds);
            return true;
        } catch (Exception e) {
            logger.error("failed to set redis key {}", redisKey, e);
        }
        return false;
    }


    @Override
    public boolean setList(String redisKey, List<T> dataList) {
        try {
            setCacheList(redisKey, dataList);
            return true;
        } catch (Exception e) {
            logger.error("failed to set redis key {}", redisKey, e);
        }
        return false;
    }

    @Override
    public <T> List<T> getList(String redisKey) {
        try {
            List<T> dataList = getCacheList(redisKey);
            return dataList;
        } catch (Exception e) {
            logger.error("failed to query redis key {}", redisKey, e);
        }
        return null;
    }

    @Override
    public boolean setSet(String redisKey, Set<T> dataSet) {
        try {
            setCacheSet(redisKey, dataSet);
            return true;
        } catch (Exception e) {
            logger.error("failed to set redis key {}", redisKey, e);
        }
        return false;
    }

    @Override
    public Set<T> getSet(String redisKey) {
        try {
            Set<T> dataSet = getCacheSet(redisKey);
            return dataSet;
        } catch (Exception e) {
            logger.error("failed to query redis key {}", redisKey, e);
        }
        return null;
    }

    @Override
    public boolean setMap(String redisKey, Map<String, T> dataMap) {
        try {
            setCacheMap(redisKey, dataMap);
            return true;
        } catch (Exception e) {
            logger.error("failed to set redis key {}", redisKey, e);
        }
        return false;
    }

    @Override
    public <T> Map<String, T> getMap(String redisKey) {
        try {
            Map<String, T> dataMap = getCacheMap(redisKey);
            return dataMap;
        } catch (Exception e) {
            logger.error("failed to query redis key {}", redisKey, e);
        }
        return null;
    }

    @Override
    public boolean setExpire(String redisKey, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(redisKey, time, TimeUnit.SECONDS);
                return true;
            }
        } catch (Exception e) {
            logger.error("failed to set expire {}s for redis key {}", time, redisKey, e);
        }
        return false;
    }

    @Override
    public boolean setExpireAt(String redisKey, Date date) {
        try {
            redisTemplate.expireAt(redisKey, date);
            return true;
        } catch (Exception e) {
            logger.error("failed to set expire at {} for redis key {}", date, redisKey, e);
        }
        return false;
    }

    @Override
    public long getExpire(String redisKey) {
        try {
            long expire = redisTemplate.getExpire(redisKey);
            return expire;
        } catch (Exception e) {
            logger.error("failed to query expire time for redis key {}", redisKey, e);
        }
        return -1;
    }

}