package com.trs.aiweishi.dagger.module;

import android.support.v4.app.FragmentActivity;

import com.lf.http.HttpHelper;
import com.lf.http.model.IDataModel;
import com.lf.http.model.impl.DataModelImpl;
import com.trs.aiweishi.app.AppAplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Liufan on 2018/5/16.
 */

@Module
public class AppModule {
    AppAplication application;
    FragmentActivity activity;

    public AppModule(AppAplication application, FragmentActivity baseActivity) {
        this.application = application;
        this.activity = baseActivity;
    }

    @Singleton
    @Provides
    public HttpHelper provideHttpHelper() {
        return new HttpHelper();
    }

    @Singleton
    @Provides
    public IDataModel provideDataModel(HttpHelper httpHelper) {
        return new DataModelImpl(httpHelper);
    }
}
