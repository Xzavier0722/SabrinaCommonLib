package com.xzavier0722.uon.sabrinaaeroplanechess.common;

import com.google.gson.Gson;

import java.util.Base64;

public class Utils {

    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static String base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String base64(String str) {
        return base64(str.getBytes());
    }

    public static byte[] debase64(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }

}
