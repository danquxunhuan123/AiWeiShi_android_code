package com.trs.aiweishi.view.ui.activity;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FindPsdNextActivity extends BaseActivity {

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_sure)
    EditText etPsdSure;

    private String userName;
    private String code;

    public static String CODE = "code";
    public static String USER_NAME = "user_name";

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        userName = getIntent().getStringExtra(USER_NAME);
        code = getIntent().getStringExtra(CODE);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_forget_psd_layout;
    }

    public void commit(View view) {
        String nPsd = etPsd.getText().toString().trim();
        String psdSure = etPsdSure.getText().toString().trim();

        if (TextUtils.isEmpty(nPsd) || TextUtils.isEmpty(psdSure)) {
            ToastUtils.showShort(getResources().getString(R.string.psd_empty_warn));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("attrName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
        params.put("attrValue", Base64.encodeToString(userName.getBytes(), Base64.DEFAULT));
        params.put("activationCode", Base64.encodeToString(code.getBytes(), Base64.DEFAULT));
        params.put("newPassword", Base64.encodeToString(nPsd.getBytes(), Base64.DEFAULT));
        params.put("ensurePassword", Base64.encodeToString(psdSure.getBytes(), Base64.DEFAULT));
        presenter.findPsd(params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0) {
            ToastUtils.showShort(getResources().getString(R.string.change_psd_success));
            ForgetPsdActivity.instance.finish();
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
