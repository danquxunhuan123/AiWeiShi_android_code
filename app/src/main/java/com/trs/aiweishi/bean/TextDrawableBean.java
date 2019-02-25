package com.trs.aiweishi.bean;

import com.lf.http.bean.BaseBean;

/**
 * Created by Liufan on 2018/6/26.
 */

public class TextDrawableBean extends BaseBean {
    public static final int TEXT_DRAWABLE_TYPE = 5; //图片布局类型

    private int drawable;
    private String name;

    public TextDrawableBean() {
        setType(TEXT_DRAWABLE_TYPE);
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
