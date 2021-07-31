package com.xzavier0722.uon.sabrinaaeroplanechess.common;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Utils {

    private static final Gson gson = new Gson();
    private static final char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final SecureRandom random = new SecureRandom();
    private static MessageDigest sha256;

    static {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    public static byte[] sha256(String str) {
        return sha256.digest(str.getBytes());
    }

    public static String getSign(String data) {
        return base64(sha256(data));
    }

}
