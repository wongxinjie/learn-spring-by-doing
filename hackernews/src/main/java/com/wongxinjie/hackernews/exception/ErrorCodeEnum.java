package com.wongxinjie.hackernews.exception;

public enum ErrorCodeEnum {

    // HTTP common error code
    notFound(404, "resource not found"),

    // user error code
    userNotExistsOrPasswordIncoreect(403, "email not registered or password not password incorrect"),
    emailExists(419, "email registered already"),
    nicknameExists(419, "nickname taken"),
    passwordIncorrect(403, "password incorrect"),
    ;

    private int code;
    private String desc;

    private ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
