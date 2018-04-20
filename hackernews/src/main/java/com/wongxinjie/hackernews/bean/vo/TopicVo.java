package com.wongxinjie.hackernews.bean.vo;

public class TopicVo {

    private String title;
    private String url;
    private int redirect;

    public TopicVo() {
    }

    public TopicVo(String title, String url, int redirect) {
        this.title = title;
        this.url = url;
        this.redirect = redirect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRedirect() {
        return redirect;
    }

    public void setRedirect(int redirect) {
        this.redirect = redirect;
    }
}
