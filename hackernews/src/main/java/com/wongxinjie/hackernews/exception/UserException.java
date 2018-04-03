package com.wongxinjie.hackernews.exception;

public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public UserException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public UserException(ErrorCodeEnum error) {
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
