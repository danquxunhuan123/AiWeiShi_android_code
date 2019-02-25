package com.lf.http;

/**
 * Created by Liufan on 2018/5/16.
 */

public interface IResponseCallBack {
    void onSuccess(Object obj);
    void onError(Throwable e);
}
