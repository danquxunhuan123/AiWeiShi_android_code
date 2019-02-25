package com.lf.http.bean;


/**
 * Created by Liufan on 2018/6/29.
 */

public class RegistBean extends BaseBean {
    private String serviceName;
    private String result;

    @Override
    public String toString() {
        return "RegistBean{" +
                "serviceName='" + serviceName + '\'' +
                ", desc='" + desc + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
