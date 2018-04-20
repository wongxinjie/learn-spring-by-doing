package com.wongxinjie.hackernews.common;

import java.net.URI;
import java.net.URISyntaxException;

public class NetworkUtils {

    public static String getDomainFromUrl(String url) {
        String domain = "";
        try {
            URI uri = new URI(url);
            domain = uri.getHost();
        } catch (URISyntaxException e) {
            return domain;
        }
        return domain.startsWith("wwww.") ? domain.substring(4) : domain;
    }


    public static void main(String[] args) {
        String url = "https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-one-mapping-example/";
        System.out.println(NetworkUtils.getDomainFromUrl(url));
    }
}
