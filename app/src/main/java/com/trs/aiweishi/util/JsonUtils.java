package com.trs.aiweishi.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Liufan on 2018/9/11.
 */

public class JsonUtils {
    public JSONObject parseJson(String result) {
        try {
            return new JSONObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
