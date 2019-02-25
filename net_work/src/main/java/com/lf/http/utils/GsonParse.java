package com.lf.http.utils;

import com.google.gson.Gson;

public class GsonParse {
    public static <T> T parse(String data,Class cla){
        return (T)new Gson().fromJson(data,cla);
    }
}
