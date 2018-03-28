package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.common.UUIDUtil;
import com.wongxinjie.hackernews.dao.UserRepository;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.HNErrorCode;
import com.wongxinjie.hackernews.exception.HNUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserRepository userDao;

    @Override
    public Long login(String email, String password) throws HNUserException {
        User row = userDao.findFirstByEmail(email);
        String encryPassword = password;

        if(row == null|| !row.getPassword().equals(encryPassword)) {
            throw new HNUserException(HNErrorCode.userNotExistsOrPasswordIncoreect);
        }
        return row.getId();
    }

    @Override
    public boolean logout(Long userId) {
        return true;
    }

    @Override
    public Long register(String email, String password) throws HNUserException {
        User row = userDao.findFirstByEmail(email);
        if(row != null) {
            throw new HNUserException(HNErrorCode.emailExists);
        }

        String entryPassword = password;
        String userName = "u_" + UUIDUtil.shortUUID();
        User model = new User(userName, email, entryPassword);
        userDao.save(model);

        return model.getId();
    }

    @Override
    public Long resetPassword(Long userId, String password, String passwordToSet) throws HNUserException {
        Optional<User> optional = userDao.findById(userId);
        if(optional.isPresent()) {
            User model = optional.get();

            String entryPassword = password;
            if(!model.getPassword().equals(entryPassword)) {
                throw new HNUserException(HNErrorCode.passwordIncorrect);
            }

            String passwordToUpdate = passwordToSet;
            model.setPassword(passwordToUpdate);
            userDao.save(model);
            return model.getId();
        } else {
            throw new HNUserException(HNErrorCode.notFound);
        }

    }

    @Override
    public Long updateProfile(Long userId, String username) throws HNUserException {
        Boolean exists = userDao.existsByIdNotAndUsername(userId,  username);
        if(exists) {
            throw new HNUserException(HNErrorCode.nicknameExists);
        }

        Optional<User> optional = userDao.findById(userId);
        if(optional.isPresent()) {
            User model = optional.get();
            model.setUsername(username);
            userDao.save(model);
            return model.getId();
        } else {
            throw new HNUserException(HNErrorCode.notFound);
        }
    }

    @Override
    public User getUserProfile(Long userId) throws HNUserException {
        Optional<User> optional = userDao.findById(userId);
        if(!optional.isPresent()) {
            throw new HNUserException(HNErrorCode.notFound);
        }
        return optional.get();
    }
}
