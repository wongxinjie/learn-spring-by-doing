package com.wongxinjie.hackernews.service.impl;

import com.wongxinjie.hackernews.bean.vo.UserResponseVO;
import com.wongxinjie.hackernews.common.CookieUtils;
import com.wongxinjie.hackernews.common.UUIDUtils;
import com.wongxinjie.hackernews.dao.UserRepository;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.ErrorCodeEnum;
import com.wongxinjie.hackernews.exception.UserException;
import com.wongxinjie.hackernews.service.LoginService;
import com.wongxinjie.hackernews.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private RedisService<User> redisService;

    @Override
    public UserResponseVO login(String email, String password) throws UserException {
        User row = userDao.findFirstByEmail(email);

        if(row == null|| !passwordEncoder.matches(password, row.getPassword())) {
            throw new UserException(ErrorCodeEnum.userNotExistsOrPasswordIncoreect);
        }

        String ticket = "HN-" + UUIDUtils.uuid();
        redisService.setexObject(ticket, row, 3600);

        UserResponseVO userResponseVO = new UserResponseVO(row.getId(), row.getUsername(), ticket);
        return userResponseVO;
    }

    @Override
    public boolean logout(Long userId) {
        return true;
    }

    @Override
    public Long register(String email, String password) throws UserException {
        User row = userDao.findFirstByEmail(email);
        if(row != null) {
            throw new UserException(ErrorCodeEnum.emailExists);
        }

        String entryPassword = passwordEncoder.encode(password);
        String userName = "u_" + UUIDUtils.shortUUID();
        User model = new User(userName, email, entryPassword);
        userDao.save(model);

        return model.getId();
    }

    @Override
    public Long resetPassword(Long userId, String password, String passwordToSet) throws UserException {
        Optional<User> optional = userDao.findById(userId);
        if(optional.isPresent()) {
            User model = optional.get();

            if(!passwordEncoder.matches(password, model.getPassword())) {
                throw new UserException(ErrorCodeEnum.passwordIncorrect);
            }

            String passwordToUpdate = passwordEncoder.encode(passwordToSet);
            model.setPassword(passwordToUpdate);
            userDao.save(model);
            return model.getId();
        } else {
            throw new UserException(ErrorCodeEnum.notFound);
        }

    }

    @Override
    public Long updateProfile(Long userId, String username) throws UserException {
        Boolean exists = userDao.existsByIdNotAndUsername(userId,  username);
        if(exists) {
            throw new UserException(ErrorCodeEnum.nicknameExists);
        }

        Optional<User> optional = userDao.findById(userId);
        if(optional.isPresent()) {
            User model = optional.get();
            model.setUsername(username);
            userDao.save(model);
            return model.getId();
        } else {
            throw new UserException(ErrorCodeEnum.notFound);
        }
    }

    @Override
    public User getUserProfile(Long userId) throws UserException {
        Optional<User> optional = userDao.findById(userId);
        if(!optional.isPresent()) {
            throw new UserException(ErrorCodeEnum.notFound);
        }
        return optional.get();
    }
}
