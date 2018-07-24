package com.trs.aiweishi.view.ui.activity;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.UserBean;
import com.trs.aiweishi.presenter.IBindPhoneView;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.util.Utils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity implements IBindPhoneView {
    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_psd)
    EditText etPsd;

    public static String USER_NAME = "user_name";
    public static String USER_PIC = "user_pic";
    public static String UID = "uid";
    public static String AUTH_SITE = "auth_site";
    public static String ACCESS_TOKEN = "access_token";
    public static String AUTH = "auth";

    private String user_name;
    private String user_pic;
    private String uid = "";
    private String auth_site = "";
    private String access_token = "";
    private int auth = 0;
    private String isBindExistUser = "true";

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    public void bind(View view) {
        if (TextUtils.isEmpty(etUsername.getText().toString().trim())
                || TextUtils.isEmpty(etPsd.getText().toString().trim())) {
            ToastUtils.showShort(getResources().getString(R.string.phone_psd_empty_warn));
            return;
        }
        requestUserInfo();
//        addAccountMapping();
    }

    private void addAccountMapping() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", Base64.encodeToString(uid.getBytes(), Base64.DEFAULT));
        params.put("authSite", Base64.encodeToString(auth_site.getBytes(), Base64.DEFAULT));//
        params.put("accessToken", Base64.encodeToString(access_token.getBytes(), Base64.DEFAULT));
        params.put("userName", Base64.encodeToString(etUsername.getText().toString().trim().getBytes(), Base64.DEFAULT));
        params.put("password", Base64.encodeToString(etPsd.getText().toString().trim().getBytes(), Base64.DEFAULT));
        params.put("isBindExistUser", Base64.encodeToString(isBindExistUser.getBytes(), Base64.DEFAULT));
        presenter.addAccountMapping(params);
    }

    private void requestUserInfo() {
        Map<String, String> param = new HashMap();
        param.put("userName", Base64.encodeToString(etUsername.getText().toString().trim().getBytes(), Base64.DEFAULT));
        presenter.getUserInfo(1, param);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    protected void initData() {
        uid = getIntent().getStringExtra(UID);
        auth_site = getIntent().getStringExtra(AUTH_SITE);
        access_token = getIntent().getStringExtra(ACCESS_TOKEN);
        auth = getIntent().getIntExtra(AUTH, 0);
        user_name = getIntent().getStringExtra(USER_NAME);
        user_pic = getIntent().getStringExtra(USER_PIC);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0 || baseBean.getCode() == 14) { // 操作成功
            loginByUID();
        } else {
            ToastUtils.showShort(baseBean.getDesc());
        }
    }

    private void loginByUID() {
        Map<String, String> params = new HashMap<>();
        params.put("coAppName", AppConstant.APP_NAME);
        params.put("uid", uid);
        params.put("authSite", auth_site);//
        params.put("accessToken", Base64.encodeToString(access_token.getBytes(), Base64.DEFAULT));
        params.put("coSessionId", Base64.encodeToString(Utils.getRandomString(5).getBytes(), Base64.DEFAULT));
        presenter.loginByUID(1, params);
    }

    @Override
    public void loginByUID(BaseBean bean) {
        UserBean userBean = (UserBean) bean;
        if (userBean.getCode() == 200) {
            spUtils.put(AppConstant.IS_LOGIN, true);
            spUtils.put(AppConstant.AUTH_SITE, auth);
            spUtils.put(AppConstant.SESSION_ID, userBean.getData().getCoSessionId());
            spUtils.put(AppConstant.USER_NAME, user_name);
            spUtils.put(AppConstant.USER_PIC, user_pic);
//            setResult(0, new Intent());
            finish();
            LoginActivity.instance.finish();
        } else {
            ToastUtils.showShort(userBean.getDesc());
        }
    }

    @Override
    public void getUserInfo(BaseBean bean) {
        if (bean.getCode() == 0) {
            isBindExistUser = "false";
        }
        if (bean.getCode() == -14) {
            isBindExistUser = "true";
        }

        addAccountMapping();
    }

}
