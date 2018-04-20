package com.wongxinjie.hackernews.bean.dto;

public class TopicDto {

    private String title;
    private String domain;
    private String when;
    private String poster;
    private int points;
    private int redirect;

    public TopicDto() {
    }

    public TopicDto(String title, String domain, String when, String poster, int points, int redirect) {
        this.title = title;
        this.domain = domain;
        this.when = when;
        this.poster = poster;
        this.points = points;
        this.redirect = redirect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRedirect() {
        return redirect;
    }

    public void setRedirect(int redirect) {
        this.redirect = redirect;
    }
}
