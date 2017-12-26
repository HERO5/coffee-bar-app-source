package com.example.apple.bakebar.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/11/2.
 */

public class JsonUtil {

    public static String objectToJson(Object obj){

        String json;
        Gson gson = new Gson();
        json = gson.toJson(obj);
        return json;

    }

    public static Object jsonToObject(String json,Class<?> cls){

        Gson gson = new Gson();
        Object obj;
        obj = gson.fromJson(json, cls);
        return obj;

    }

    public static <T> List<T> jsonsToObjects(String jsons, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsons).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
