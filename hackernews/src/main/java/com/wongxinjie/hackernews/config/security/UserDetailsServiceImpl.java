package com.wongxinjie.hackernews.config.security;

import com.wongxinjie.hackernews.entity.User;
import com.wongxinjie.hackernews.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    RedisService<User> redisService;

    /**
     * find account by user's registered email
     * @param key cookie key
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
        User user = redisService.getObject(key);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("No user found with cookie '%s'.", key));
        } else {
            return UserFactory.create(user);
        }
    }
}
