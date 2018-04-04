package com.wongxinjie.hackernews.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface RedisService<T> {

    /**
     * 移除redis
     */
    boolean remove(String redisKey);

    /**
     * 判断key是否存在
     */
    boolean containsKey(String redisKey);

    /**
     * 缓存基本对象
     */
    boolean setObject(String redisKey, T value);

    /**
     * 获取缓存的对象
     */
    <T> T getObject(String redisKey);

    /**
     * 写入数据，同时设置过期时间。
     *
     * @param redisKey
     * @param value
     * @param seconds
     * @return
     */
    boolean setexObject(String redisKey, T value, long seconds);


    /**
     * 缓存list
     */
    boolean setList(String redisKey, List<T> dataList);

    /**
     * 获取缓存的list
     */
    <T> List<T> getList(String redisKey);

    /**
     * 缓存set
     */
    boolean setSet(String redisKey, Set<T> dataSet);

    /**
     * 获取缓存的set
     */
    Set<T> getSet(String redisKey);

    /**
     * 缓存Map
     */
    boolean setMap(String redisKey, Map<String, T> dataMap);

    /**
     * 获取缓存的Map
     */
    <T> Map<String, T> getMap(String redisKey);

    /**
     * 设置过期时间，单位秒
     */
    boolean setExpire(String redisKey, long time);

    /**
     * 设置具体过期时间
     */
    boolean setExpireAt(String redisKey, Date date);

    /**
     * 查询剩余过期时间，单位秒
     */
    long getExpire(String redisKey);
}

