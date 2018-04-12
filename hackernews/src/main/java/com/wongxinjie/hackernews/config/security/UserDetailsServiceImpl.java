package com.wongxinjie.hackernews.config.security;

import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.repository.SessionRepository;
import org.h2.engine.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SessionRepository sessionRepository;

    /**
     * find account by user's registered email
     * @param key cookie key
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
        User user = sessionRepository.getUserFromSession(key);

        if(user != null) {
            return UserFactory.create(user);
        }
        return null;
    }
}
