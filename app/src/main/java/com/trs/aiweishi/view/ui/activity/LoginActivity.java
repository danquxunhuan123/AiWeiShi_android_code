package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.UserBean;
import com.trs.aiweishi.listener.MyUMAuthListener;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.view.IUserView;
import com.trs.aiweishi.view.ui.fragment.UserFragment;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements IUserView, MyUMAuthListener.OnLoginSuccessListener {

    @Inject
    MyUMAuthListener umAuthListener;
    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_username)
    EditText userName;
    @BindView(R.id.et_psd)
    EditText psd;

    public static LoginActivity instance = null;

    private String authSite = "";
    private int auth = 0;
    private Map<String, String> data;

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        instance = this;
        umAuthListener.setOnThirdSucListener(this);
    }

    @Override
    public int initLayout() {
        return R.layout.login_layout;
    }


    public void login(View view) {
        String userPhone = userName.getText().toString().trim();
        String psd = this.psd.getText().toString().trim();
        if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(psd)) {
            ToastUtils.showShort(getResources().getString(R.string.empty_warn));
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("Version", "v5.0");
        params.put("coAppName", "IWSApp");
        String psdStr = "loginType=mobile;loginKey=" + userPhone;
        params.put("userName", psdStr);
        params.put("password", psd);
        params.put("coSessionId", Base64.encodeToString(Utils.getRandomString(5).getBytes(), Base64.DEFAULT));
        presenter.login(0, params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        UserBean bean = (UserBean) baseBean;
        if (bean.getCode() == 200) {
            spUtils.put(AppConstant.IS_LOGIN, true);
            spUtils.put(AppConstant.AUTH_SITE, 0);
            spUtils.put(AppConstant.SESSION_ID, bean.getData().getCoSessionId());
            spUtils.put(AppConstant.USER_PHONE, bean.getData().getUser().getUserName());
            spUtils.put(AppConstant.UER_PSD, psd.getText().toString().trim());
            Intent result = new Intent();
            result.putExtra(UserFragment.USER, (Parcelable) bean);
            setResult(0, result);
            finish();
            ToastUtils.showShort(getResources().getString(R.string.login_success));
        } else {
            ToastUtils.showShort(bean.getDesc());
        }
    }

    @OnClick({R.id.tv_regist, R.id.tv_forget_psd, R.id.iv_back
            , R.id.ll_qq_login, R.id.ll_weixin_login, R.id.ll_weibo_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.tv_forget_psd:
                startActivity(new Intent(this, ForgetPsdActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_qq_login:
                authSite = "QQ";
                auth = 1;
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.ll_weixin_login:
                authSite = "";
                auth = 2;
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.ll_weibo_login:
                authSite = "sinaWeibo";
                auth = 3;
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnThirdSeccess(Map<String, String> data) {
        this.data = data;
        Map<String, String> params = new HashMap<>();
        params.put("uid", Base64.encodeToString(data.get(MyUMAuthListener.uid).getBytes(), Base64.DEFAULT));
        params.put("authSite", Base64.encodeToString(authSite.getBytes(), Base64.DEFAULT));
        params.put("accessToken", Base64.encodeToString(data.get(MyUMAuthListener.accessToken).getBytes(), Base64.DEFAULT));
        presenter.checkAccountMapping(params);
    }

    @Override
    public void checkAccountMapping(BaseBean obj) {
        if (obj.getCode() == 15) { //未绑定 进行绑定
            Intent intent = new Intent(this, BindPhoneActivity.class);
            intent.putExtra(BindPhoneActivity.UID,data.get(MyUMAuthListener.uid));
            intent.putExtra(BindPhoneActivity.AUTH_SITE,authSite);
            intent.putExtra(BindPhoneActivity.ACCESS_TOKEN,data.get(MyUMAuthListener.accessToken));
            intent.putExtra(BindPhoneActivity.AUTH,auth);
            intent.putExtra(BindPhoneActivity.USER_NAME,data.get(MyUMAuthListener.name));
            intent.putExtra(BindPhoneActivity.USER_PIC,data.get(MyUMAuthListener.iconurl));
            startActivity(intent);
//            addAccountMapping();
        } else if (obj.getCode() == 14){  //已绑定 进行登录
            loginByUID();
        }else { //其他错误
            ToastUtils.showShort(obj.getDesc());
        }
    }

    @Override
    public void addAccountMapping(BaseBean obj) {
        if (obj.getCode() == 0 || obj.getCode() == 14) { // 操作成功
//            loginByUID();
        } else{
            ToastUtils.showShort(obj.getDesc());
        }
    }

    private void loginByUID(){
        Map<String, String> params = new HashMap<>();
        params.put("coAppName", AppConstant.APP_NAME);
        params.put("uid",data.get(MyUMAuthListener.uid));
        params.put("authSite", authSite);//
        params.put("accessToken", Base64.encodeToString(data.get(MyUMAuthListener.accessToken).getBytes(), Base64.DEFAULT));
        params.put("coSessionId", Base64.encodeToString(Utils.getRandomString(5).getBytes(), Base64.DEFAULT));
        presenter.loginByUID(0,params);
    }

    @Override
    public void thirdLoginSuccess(BaseBean bean) {
        UserBean userBean = (UserBean) bean;
        if (userBean.getCode() == 200) {
            spUtils.put(AppConstant.IS_LOGIN, true);
            spUtils.put(AppConstant.AUTH_SITE, auth);
            spUtils.put(AppConstant.SESSION_ID, userBean.getData().getCoSessionId());
            spUtils.put(AppConstant.USER_NAME, data.get(MyUMAuthListener.name));
            spUtils.put(AppConstant.USER_PIC, data.get(MyUMAuthListener.iconurl));
            setResult(0, new Intent());
            finish();
        } else {
            ToastUtils.showShort(userBean.getDesc());
        }
    }

}
