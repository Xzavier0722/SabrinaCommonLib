package com.xzavier0722.uon.sabrinaaeroplanechess.common;

import com.google.gson.Gson;

public class Utils {

    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

}
