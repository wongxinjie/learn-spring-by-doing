package com.wongxinjie.hackernews.common;

import com.sun.deploy.net.HttpResponse;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    private static final int maxAge = 3600;

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie.getValue();
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if(cookie == null) {
            cookie = new Cookie(name, value);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);
        } else {
            cookie.setValue(value);
        }
        response.addCookie(cookie);
    }
}
