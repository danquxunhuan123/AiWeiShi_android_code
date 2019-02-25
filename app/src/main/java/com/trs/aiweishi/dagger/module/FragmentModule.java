package com.trs.aiweishi.dagger.module;

import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.dagger.scope.FragmentScope;
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
public class FragmentModule {
    private BaseFragment baseFragment;

    public FragmentModule(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    @FragmentScope
    @Provides
    public IHomePresenter provideMainPresenter(IDataModel dataModel) {  //
        return new HomePresenterImpl(baseFragment,dataModel);
    }

    @FragmentScope
    @Provides
    public IUserPresenter provideUserPresenter(IDataModel dataModel) {
        return new UserPresenterImpl(baseFragment,dataModel);
    }
}
