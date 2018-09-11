package com.trs.aiweishi.di.component;

import com.trs.aiweishi.di.module.ActivityModule;
import com.trs.aiweishi.di.scope.ActivityScope;
import com.trs.aiweishi.view.ui.activity.BindPhoneActivity;
import com.trs.aiweishi.view.ui.activity.ChangePsdActivity;
import com.trs.aiweishi.view.ui.activity.CheckActivity;
import com.trs.aiweishi.view.ui.activity.CheckDetailActivity;
import com.trs.aiweishi.view.ui.activity.CheckSearchActivity;
import com.trs.aiweishi.view.ui.activity.DetailActivity;
import com.trs.aiweishi.view.ui.activity.FeedBackActivity;
import com.trs.aiweishi.view.ui.activity.FindPsdNextActivity;
import com.trs.aiweishi.view.ui.activity.ForgetPsdActivity;
import com.trs.aiweishi.view.ui.activity.ListDataActivity;
import com.trs.aiweishi.view.ui.activity.LoginActivity;
import com.trs.aiweishi.view.ui.activity.MainActivity;
import com.trs.aiweishi.view.ui.activity.MyBookingActivity;
import com.trs.aiweishi.view.ui.activity.MyQuestionActivity;
import com.trs.aiweishi.view.ui.activity.NgoListActivity;
import com.trs.aiweishi.view.ui.activity.RegistActivity;
import com.trs.aiweishi.view.ui.activity.RegistNextActivity;
import com.trs.aiweishi.view.ui.activity.SearchActivity;
import com.trs.aiweishi.view.ui.activity.UserConfigActivity;
import com.trs.aiweishi.view.ui.activity.ZiXunActivity;

import dagger.Component;

/**
 * Created by Liufan on 2018/5/16.
 */

@ActivityScope
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
    void inject(CheckActivity checkActivity);
    void inject(CheckDetailActivity checkDetailActivity);
    void inject(ZiXunActivity ziXunActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegistActivity registActivity);
    void inject(RegistNextActivity registNextActivity);
    void inject(UserConfigActivity userConfigActivity);
    void inject(ListDataActivity listDataActivity);
    void inject(SearchActivity searchActivity);
    void inject(ChangePsdActivity changePsdActivity);
    void inject(ForgetPsdActivity forgetPsdActivity);
    void inject(CheckSearchActivity checkSearchActivity);
    void inject(BindPhoneActivity bindPhoneActivity);
    void inject(FindPsdNextActivity findPsdNextActivity);
    void inject(FeedBackActivity feedBackActivity);
    void inject(MyQuestionActivity myQuestionActivity);

    void inject(NgoListActivity ngoListActivity);

    void inject(MyBookingActivity myBookingActivity);
}
