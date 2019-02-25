package com.trs.aiweishi.dagger.module;

import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.dagger.scope.ActivityScope;
import com.trs.aiweishi.listener.MyUMAuthListener;
import com.lf.http.model.IDataModel;
import com.lf.http.presenter.IHomePresenter;
import com.lf.http.presenter.IUserPresenter;
import com.lf.http.presenter.impl.HomePresenterImpl;
import com.lf.http.presenter.impl.UserPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Liufan on 2018/5/16.
 */

@Module
public class ActivityModule {
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @ActivityScope
    @Provides
    public IHomePresenter providesHomePresenter(IDataModel dataModel){
        return new HomePresenterImpl( baseActivity,dataModel);
    }

    @ActivityScope
    @Provides
    public IUserPresenter providesUserPresenter(IDataModel dataModel){
        return new UserPresenterImpl( baseActivity,dataModel);
    }

    @ActivityScope
    @Provides
    public MyUMAuthListener providesUMAuthListener(){
        return new MyUMAuthListener(baseActivity);
    }
}
