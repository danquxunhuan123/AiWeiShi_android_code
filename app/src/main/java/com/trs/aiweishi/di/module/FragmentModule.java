package com.trs.aiweishi.di.module;

import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.di.scope.FragmentScope;
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
