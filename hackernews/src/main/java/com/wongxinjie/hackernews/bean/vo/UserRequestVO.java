package com.wongxinjie.hackernews.bean.vo;


public class UserRequestVO {

    private String email;
    private String username;
    private String password;
    private String updatedPassword;

    public UserRequestVO(String email, String nickname, String password, String updatedPassword) {
        this.email = email;
        this.username = nickname;
        this.password = password;
        this.updatedPassword = updatedPassword;
    }

    public UserRequestVO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUpdatedPassword() {
        return updatedPassword;
    }

    public void setUpdatedPassword(String updatedPassword) {
        this.updatedPassword = updatedPassword;
    }
}
