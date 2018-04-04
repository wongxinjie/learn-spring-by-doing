package com.wongxinjie.hackernews.common;

import java.util.UUID;

public class UUIDUtils {

    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        String[] uuidParts = uuid.toString().split("-");
        return uuidParts[0];
    }

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
