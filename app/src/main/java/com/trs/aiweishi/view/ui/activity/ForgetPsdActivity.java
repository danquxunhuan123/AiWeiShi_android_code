package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.view.IFindPsdView;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPsdActivity extends BaseActivity implements IFindPsdView {

    public static ForgetPsdActivity instance;
    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yanzhengma)
    EditText etYanzhengma;

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        instance = this;
    }

    @Override
    public int initLayout() {
        return R.layout.forget_psd_layout;
    }


    public void getCode(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("attributeName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
        params.put("attributeValue", Base64.encodeToString(etPhone.getText().toString().getBytes(), Base64.DEFAULT));
        presenter.getCode(1, params);
    }

    @Override
    public void getCodeSuccess(BaseBean obj) {
        if (obj.getCode() == 0) {
            ToastUtils.showShort("发送成功");
        } else {
            ToastUtils.showShort(obj.getDesc());
        }
    }

    @Override
    public void notifyCodeSuccess(BaseBean bean) {
        if (bean.getCode() == 0) {
            Intent intent = new Intent(this, FindPsdNextActivity.class);
            intent.putExtra(FindPsdNextActivity.USER_NAME, etPhone.getText().toString().trim());
            intent.putExtra(FindPsdNextActivity.CODE, etYanzhengma.getText().toString().trim());
            startActivity(intent);
        } else {
            ToastUtils.showShort(bean.getDesc());
        }
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
    public void onViewClicked(View view) {
        finish();
    }

    public void nextFind(View view) {
//        String phone = etPhone.getText().toString().trim();
//        String yzm = etYanzhengma.getText().toString().trim();
//        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(yzm)){
//            ToastUtils.showShort(getResources().getString(R.string.code_empty_warn));
//            return;
//        }
//
//        Map<String, String> params = new HashMap<>();
//        params.put("attributeName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
//        params.put("attributeValue", Base64.encodeToString(phone.getBytes(), Base64.DEFAULT));
//        params.put("activationCode", Base64.encodeToString(yzm.trim().getBytes(), Base64.DEFAULT));
//        params.put("confirmType", Base64.encodeToString("999".getBytes(), Base64.DEFAULT));
//        presenter.notifyUserAttr(1,params);

        Intent intent = new Intent(this, FindPsdNextActivity.class);
        intent.putExtra(FindPsdNextActivity.USER_NAME, etPhone.getText().toString().trim());
        intent.putExtra(FindPsdNextActivity.CODE, etYanzhengma.getText().toString().trim());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
