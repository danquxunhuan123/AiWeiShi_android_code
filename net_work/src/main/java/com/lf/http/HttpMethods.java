package com.lf.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liufan on 2018/5/16.
 */
public class HttpMethods {
    private static final int DEFAULT_TIMEOUT = 60;
    protected RetofitApi info;
    public static final String OK_HTTP = "okHttpUtil";

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods(AppConstant.BASE_URL);
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HttpMethods(String url) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
//                LogUtils.dTag(Logger.OK_HTTP, message);
                Log.d(OK_HTTP, message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
        ;

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        info = retrofit.create(RetofitApi.class);
    }
}
