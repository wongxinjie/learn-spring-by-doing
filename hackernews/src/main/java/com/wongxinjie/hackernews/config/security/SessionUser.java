package com.wongxinjie.hackernews.config.security;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUser implements UserDetails, Serializable {

    private static final Long serialVersionUID = 1L;

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public SessionUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
