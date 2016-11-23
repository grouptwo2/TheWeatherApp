package com.app.theweatherapp.util;

import com.google.gson.Gson;

/**
 * Created by Beyeta Sanu on 11/19/16.
 */

public class JSONUtil {
    private static final Gson GSON = new Gson();

    public static <T> T fromJSON(String jsonData, Class<T> classOfT) {
        return GSON.fromJson(jsonData, classOfT);
    }

    public <T> String toJSON(T o) {
        return GSON.toJson(o);
    }
}
