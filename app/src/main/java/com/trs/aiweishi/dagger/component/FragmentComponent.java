package com.trs.aiweishi.dagger.component;

import com.trs.aiweishi.dagger.module.FragmentModule;
import com.trs.aiweishi.dagger.scope.FragmentScope;
import com.trs.aiweishi.view.ui.fragment.CheckFragment;
import com.trs.aiweishi.view.ui.fragment.DocFragment;
import com.trs.aiweishi.view.ui.fragment.HomeFragment;
import com.trs.aiweishi.view.ui.fragment.NgoFragment;
import com.trs.aiweishi.view.ui.fragment.UserFragment;
import com.trs.aiweishi.view.ui.fragment.ZiXunFragment;

import dagger.Component;

/**
 * Created by Liufan on 2018/5/16.
 */

@FragmentScope
@Component(modules = FragmentModule.class,dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(HomeFragment homeFragment);
    void inject(DocFragment consultFragment);
    void inject(UserFragment userFragment);
    void inject(CheckFragment checkFragment);
    void inject(ZiXunFragment ziXunFragment);
    void inject(NgoFragment ngoFragment);
}
