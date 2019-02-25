package com.trs.aiweishi.dagger.component;

import com.trs.aiweishi.dagger.module.AppModule;
import com.lf.http.model.IDataModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Liufan on 2018/5/16.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    IDataModel getDataModel();
}
