package com.wongxinjie.hackernews.common;

import java.util.UUID;

public class UUIDUtil {

    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        String[] uuidParts = uuid.toString().split("-");
        return uuidParts[0];
    }
}
