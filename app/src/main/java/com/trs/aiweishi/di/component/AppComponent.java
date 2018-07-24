package com.trs.aiweishi.di.component;

import com.trs.aiweishi.di.module.AppModule;
import com.trs.aiweishi.model.IDataModel;

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
