package com.lf.http.bean;

import java.io.Serializable;

/**
 * Created by Liufan on 2018/6/1.
 */

public class BaseBean implements Serializable {
    public static final int ITEM_LOAD_MORE = 100; //加载更多类型
    protected int code;
    protected String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                '}';
    }
}
