package com.wongxinjie.hackernews.bean;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public ResultBean() {
        code = 200;
        message = "success";
    }

    public ResultBean(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }

    public ResultBean(Throwable cause) {
        super();
        this.code = 500;
        this.message = cause.getMessage();
    }

    public ResultBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
