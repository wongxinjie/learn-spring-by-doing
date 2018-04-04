package com.wongxinjie.hackernews.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserResponseVO {

    private long id;
    private String username;

    @JsonIgnore
    private String ticket;

    public UserResponseVO() {
    }

    public UserResponseVO(long id, String username, String ticket) {
        this.id = id;
        this.username = username;
        this.ticket = ticket;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
