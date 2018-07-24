package com.trs.aiweishi.view.ui.activity;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.view.IBaseView;
import com.trs.aiweishi.view.IUserView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegistNextActivity extends BaseActivity{

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_sure)
    EditText etPsdSure;
    @BindView(R.id.but_regist)
    Button butRegist;
    String userName;

    public static String param = "tool_bar_name";
    public static String USER_NAME = "user_name";

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        userName = getIntent().getStringExtra(USER_NAME);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_regist_next;
    }

    public void commit(View view) {
        String psd = etPsd.getText().toString().trim();
        String psdSure = etPsdSure.getText().toString().trim();

        if (TextUtils.isEmpty(psd) || TextUtils.isEmpty(psdSure)) {
            ToastUtils.showShort(getResources().getString(R.string.psd_empty_warn));
            return;
        }

        Map<String, String> params = new HashMap<>();
//        if (TextUtils.isEmpty(userName))
//            userName = spUtils.getString(AppConstant.USER_PHONE);
        params.put("userName", Base64.encodeToString(userName.getBytes(), Base64.DEFAULT));
        params.put("mobile", Base64.encodeToString(userName.getBytes(), Base64.DEFAULT));
        params.put("password", Base64.encodeToString(psd.getBytes(), Base64.DEFAULT));
        presenter.regist(params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
//        RegistBean bean = (RegistBean) baseBean;
        if (baseBean.getCode() == 1009) {
            ToastUtils.showShort(getResources().getString(R.string.regist_success));
            RegistActivity.instance.finish();
            finish();
        } else {
            ToastUtils.showShort(baseBean.getDesc());
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
