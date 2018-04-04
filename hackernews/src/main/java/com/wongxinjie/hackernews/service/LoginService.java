package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.bean.vo.UserResponseVO;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.UserException;

public interface LoginService {

    /**
     *
     * @param email
     * @param password
     * @return
     * @throws UserException
     */
    UserResponseVO login(String email, String password) throws UserException;

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
     * @throws UserException
     */
    Long register(String email, String password) throws UserException;

    /**
     *
     * @param userId
     * @param password
     * @param passwordToSet
     * @return
     * @throws UserException
     */
    Long resetPassword(Long userId, String password, String passwordToSet) throws UserException;

    /**
     *
     * @param userId
     * @param nickname
     * @return
     * @throws UserException
     */
    Long updateProfile(Long userId, String nickname) throws UserException;

    /**
     *
     * @param userId
     * @return
     * @throws UserException
     */
    User getUserProfile(Long userId) throws UserException;
}
