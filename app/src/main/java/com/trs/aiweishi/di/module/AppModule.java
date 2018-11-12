package com.trs.aiweishi.di.module;

import android.support.v4.app.FragmentActivity;

import com.trs.aiweishi.app.AppAplication;
import com.trs.aiweishi.http.HttpHelper;
import com.trs.aiweishi.model.IDataModel;
import com.trs.aiweishi.model.impl.DataModelImpl;

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
        return new HttpHelper(activity);
    }

    @Singleton
    @Provides
    public IDataModel provideDataModel(HttpHelper httpHelper) {
        return new DataModelImpl(httpHelper);
    }
}
