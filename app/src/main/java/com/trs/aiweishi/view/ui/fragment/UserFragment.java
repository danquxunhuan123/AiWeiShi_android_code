package com.trs.aiweishi.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.UserData;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.util.DataCleanManager;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.IUserCenterView;
import com.trs.aiweishi.view.ui.activity.LoginActivity;
import com.trs.aiweishi.view.ui.activity.UserConfigActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Liufan on 2018/5/17.
 */
public class UserFragment extends BaseFragment implements IUserCenterView {

    @Inject
    IUserPresenter presenter;

    @BindView(R.id.iv_user_pic)
    ImageView ivUserPic;
    @BindView(R.id.tv_user_name)
    TextView userName;
    @BindView(R.id.tv_version)
    TextView version;
    @BindView(R.id.tv_cache)
    TextView cache;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_news_center)
    TextView tvNewsCenter;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.ll_check_version)
    LinearLayout llCheckVersion;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.ib_config)
    ImageButton config;

    public static final String PARAM1 = "param1";
    public static final String PARAM2 = "param2";
    public static final String USER = "user";

    private UserData user;
    private final int RESULT_USER = 100;
    private final int RESULT_CONFIG = 101;
    private SPUtils sp;

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(PARAM1, param1);
        args.putString(PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponent() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PARAM1);
            mParam2 = getArguments().getString(PARAM2);
        }
        sp = SPUtils.getInstance(AppConstant.SP_NAME);

        String versionName = context.getResources().getString(R.string.version_name);
        version.setText(String.format(versionName, AppUtils.getAppVersionName()));
        try {
            cache.setText(DataCleanManager.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestUserInfo() {
        Map<String, String> param = new HashMap();
        param.put("userName", Base64.encodeToString(sp.getString(AppConstant.USER_PHONE).getBytes(), Base64.DEFAULT));
        presenter.getUserInfo(0,param);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @OnClick({R.id.ib_config, R.id.iv_user_pic, R.id.ll_clear_cache})
    public void toConfig(View view) {
        switch (view.getId()) {
            case R.id.ib_config:
                Intent intent = new Intent(context, UserConfigActivity.class);
                intent.putExtra(USER, user.getEntry());
                startActivityForResult(intent, RESULT_CONFIG);
                break;
            case R.id.iv_user_pic:
                startActivityForResult(new Intent(context, LoginActivity.class), RESULT_USER);
                break;
            case R.id.ll_clear_cache:
                try {
                    DataCleanManager.clearAllCache(context);
                    cache.setText(DataCleanManager.getTotalCacheSize(context));
                    ToastUtils.showShort("清除成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sp.getBoolean(AppConstant.IS_LOGIN)) {
            loginShow();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_USER:
                //login
                if (data != null) {
//                    loginShow();
                }

                break;
            case RESULT_CONFIG:
                //logout
                if (!sp.getBoolean(AppConstant.IS_LOGIN)) {
                    logoutShow();
                }
                break;
            default:
                break;
        }
    }

    private void logoutShow() {
        config.setVisibility(View.INVISIBLE);
        ivUserPic.setClickable(true);

        userName.setText(context.getResources().getString(R.string.login));
        GlideUtils.loadLocalImg(context, R.mipmap.icon_user_pic_default, ivUserPic);
    }

    private void loginShow() {
        config.setVisibility(View.VISIBLE);
        ivUserPic.setClickable(false);

        if (sp.getInt(AppConstant.AUTH_SITE) != 0) { //三方登录
            user = new UserData();
            UserData.User entry = new UserData.User();
            entry.setNickName(sp.getString(AppConstant.USER_NAME));
            entry.setHeadUrl(sp.getString(AppConstant.USER_PIC));
            user.setEntry(entry);

            GlideUtils.loadCircleUrlImg(context, user.getEntry().getHeadUrl(), ivUserPic);
            userName.setText(sp.getString(AppConstant.USER_NAME));
        } else //普通登录
            requestUserInfo();
    }

    @Override
    public void getUserInfo(BaseBean bean) {
        user = (UserData) bean;
        if (user.getEntry() != null) {
            if (TextUtils.isEmpty(user.getEntry().getHeadUrl())){
                GlideUtils.loadLocalImg(context,R.mipmap.icon_user_pic_default,ivUserPic);
            }else{
                GlideUtils.loadCircleUrlImg(context, AppConstant.HEAD_URL + user.getEntry().getHeadUrl(), ivUserPic);
            }

            if (TextUtils.isEmpty(user.getEntry().getNickName())){
                userName.setText(sp.getString(AppConstant.USER_PHONE));
            }else{
                userName.setText(user.getEntry().getNickName());
            }
        }
    }

    @Override
    public void showSuccess(BaseBean baseBean) {

    }
}
