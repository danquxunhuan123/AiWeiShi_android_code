package com.trs.aiweishi.di.module;

import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.di.scope.ActivityScope;
import com.trs.aiweishi.listener.MyUMAuthListener;
import com.trs.aiweishi.model.IDataModel;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.presenter.impl.HomePresenterImpl;
import com.trs.aiweishi.presenter.impl.UserPresenterImpl;
import com.trs.aiweishi.view.IBaseView;

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
