package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/8/24.
 */

public class UpdateBean extends BaseBean {
    private int msgcode;
    private String Android_CurrentVersion;
    private String Android_url;
    private String Android_boxmsg;
    private String iOS_CurrentVersion;
    private String iOS_url;
    private String iOS_boxmsg;

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public String getAndroid_CurrentVersion() {
        return Android_CurrentVersion;
    }

    public void setAndroid_CurrentVersion(String android_CurrentVersion) {
        Android_CurrentVersion = android_CurrentVersion;
    }

    public String getAndroid_url() {
        return Android_url;
    }

    public void setAndroid_url(String android_url) {
        Android_url = android_url;
    }

    public String getAndroid_boxmsg() {
        return Android_boxmsg;
    }

    public void setAndroid_boxmsg(String android_boxmsg) {
        Android_boxmsg = android_boxmsg;
    }
}
