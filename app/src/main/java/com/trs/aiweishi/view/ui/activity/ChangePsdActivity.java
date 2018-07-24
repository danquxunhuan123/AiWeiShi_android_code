package com.trs.aiweishi.view.ui.activity;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.view.IUserView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePsdActivity extends BaseActivity{

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_psd_sure)
    EditText etPsdSure;
    @BindView(R.id.et_old_psd)
    EditText oldPsd;
    @BindView(R.id.et_new_psd)
    EditText newPsd;
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
        return R.layout.activity_psd_layout;
    }

    public void commit(View view) {
        String oPsd = oldPsd.getText().toString().trim();
        String nPsd = newPsd.getText().toString().trim();
        String psdSure = etPsdSure.getText().toString().trim();

        if (TextUtils.isEmpty(oPsd)
                || TextUtils.isEmpty(nPsd) || TextUtils.isEmpty(psdSure)) {
            ToastUtils.showShort(getResources().getString(R.string.psd_empty_warn));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("attrName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
        params.put("attrValue", Base64.encodeToString(spUtils.getString(AppConstant.USER_PHONE).getBytes(), Base64.DEFAULT));
        params.put("oldPassword", Base64.encodeToString(oPsd.getBytes(), Base64.DEFAULT));
        params.put("newPassword", Base64.encodeToString(nPsd.getBytes(), Base64.DEFAULT));
        params.put("ensurePassword", Base64.encodeToString(psdSure.getBytes(), Base64.DEFAULT));
        presenter.changePsd(params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0) {
            ToastUtils.showShort(getResources().getString(R.string.change_psd_success));
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
