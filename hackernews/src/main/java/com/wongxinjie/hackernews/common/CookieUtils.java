package com.wongxinjie.hackernews.common;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    private static final int maxAge = 3600;

    public static String getCookieValue(HttpServletRequest request, String name) {
        String value = null;

        Cookie cookie = WebUtils.getCookie(request, name);
        if(cookie != null) {
            value = cookie.getValue();
        }
        return value;
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

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
    }
}
