package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.HNUserException;

public interface LoginService {

    /**
     *
     * @param email
     * @param password
     * @return
     * @throws HNUserException
     */
    Long login(String email, String password) throws HNUserException;

    /**
     *
     * @param userId
     * @return
     */
    boolean logout(Long userId);

    /**
     *
     * @param email
     * @param password
     * @return
     * @throws HNUserException
     */
    Long register(String email, String password) throws HNUserException;

    /**
     *
     * @param userId
     * @param password
     * @param passwordToSet
     * @return
     * @throws HNUserException
     */
    Long resetPassword(Long userId, String password, String passwordToSet) throws HNUserException;

    /**
     *
     * @param userId
     * @param nickname
     * @return
     * @throws HNUserException
     */
    Long updateProfile(Long userId, String nickname) throws HNUserException;

    /**
     *
     * @param userId
     * @return
     * @throws HNUserException
     */
    User getUserProfile(Long userId) throws HNUserException;
}
