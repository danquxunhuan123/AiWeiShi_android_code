package com.trs.aiweishi.http;

import com.blankj.utilcode.BuildConfig;
import com.blankj.utilcode.util.LogUtils;
import com.trs.aiweishi.app.AppConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Liufan on 2018/6/30.
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (BuildConfig.DEBUG) {
            LogUtils.dTag(AppConstant.OK_HTTP,
                    request.url(), chain.connection(), request.headers());
        }
        return chain.proceed(request);
    }
}
