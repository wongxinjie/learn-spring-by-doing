package com.wongxinjie.hackernews.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 128)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false)
    private int status;

    @Column(name="register_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerAt;

    public Account(long id , String username, String email, int status, Date registerAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.status = status;
        this.registerAt = registerAt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getRegisterAt() {
        return registerAt;
    }

}
