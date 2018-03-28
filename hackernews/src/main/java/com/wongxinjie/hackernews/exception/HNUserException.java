package com.wongxinjie.hackernews.exception;

public class HNUserException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public HNUserException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HNUserException(HNErrorCode error) {
        this.code = error.getCode();
        this.message = error.getDesc();
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
}
