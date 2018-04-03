package com.wongxinjie.hackernews.service;

import com.wongxinjie.hackernews.common.UUIDUtil;
import com.wongxinjie.hackernews.dao.UserRepository;
import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.exception.ErrorCodeEnum;
import com.wongxinjie.hackernews.exception.UserException;
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
    public Long login(String email, String password) throws UserException {
        User row = userDao.findFirstByEmail(email);
        String encryPassword = password;

        if(row == null|| !row.getPassword().equals(encryPassword)) {
            throw new UserException(ErrorCodeEnum.userNotExistsOrPasswordIncoreect);
        }
        return row.getId();
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

        String entryPassword = password;
        String userName = "u_" + UUIDUtil.shortUUID();
        User model = new User(userName, email, entryPassword);
        userDao.save(model);

        return model.getId();
    }

    @Override
    public Long resetPassword(Long userId, String password, String passwordToSet) throws UserException {
        Optional<User> optional = userDao.findById(userId);
        if(optional.isPresent()) {
            User model = optional.get();

            String entryPassword = password;
            if(!model.getPassword().equals(entryPassword)) {
                throw new UserException(ErrorCodeEnum.passwordIncorrect);
            }

            String passwordToUpdate = passwordToSet;
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
