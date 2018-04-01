package com.wongxinjie.hackernews.config.security;

import java.util.List;
import java.util.stream.Collectors;

import com.wongxinjie.hackernews.entity.Role;
import com.wongxinjie.hackernews.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserFactory {

    private UserFactory() {
    }

    static SessionUser create(User user) {
        return new SessionUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
