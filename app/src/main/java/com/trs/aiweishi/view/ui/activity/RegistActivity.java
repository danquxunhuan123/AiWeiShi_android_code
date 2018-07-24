package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.view.IRegistView;
import com.trs.aiweishi.view.IUserView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class RegistActivity extends BaseActivity implements IRegistView{
    @Inject
    IUserPresenter presenter;

//    @BindView(R.id.et_name)
//    EditText etName;
//    @BindView(R.id.et_psd)
//    EditText etPsd;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yanzhengma)
    EditText etYanzhengma;

    public static RegistActivity instance;


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
        return R.layout.regist_layout;
    }

    public void nextRegist(View view) {
//        Intent intent = new Intent(this,RegistNextActivity.class);
//        intent.putExtra(RegistNextActivity.USER_NAME,etPhone.getText().toString().trim());
//        startActivity(intent);
        String phone = etPhone.getText().toString().trim();
        String yzm = etYanzhengma.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(yzm)){
            ToastUtils.showShort(getResources().getString(R.string.code_empty_warn));
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("attributeName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
        params.put("attributeValue", Base64.encodeToString(phone.getBytes(), Base64.DEFAULT));
        params.put("activationCode", Base64.encodeToString(yzm.trim().getBytes(), Base64.DEFAULT));
        params.put("confirmType", Base64.encodeToString("999".getBytes(), Base64.DEFAULT));
        presenter.notifyUserAttr(0,params);
    }

    public void userXieYi(View view) {
    }

    public void getCode(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("attributeName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
        params.put("attributeValue", Base64.encodeToString(etPhone.getText().toString().trim().getBytes(), Base64.DEFAULT));
        presenter.getCode(0,params);
    }

    @OnClick({R.id.tv_aggrement,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_aggrement:
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0){
            ToastUtils.showShort("发送成功");
        }else{
            ToastUtils.showShort(baseBean.getDesc());
        }
    }


    @Override
    public void notifyCodeSuccess(BaseBean bean) {
        if (bean.getCode() == 0){
            Intent intent = new Intent(this,RegistNextActivity.class);
            intent.putExtra(RegistNextActivity.USER_NAME,etPhone.getText().toString().trim());
            startActivity(intent);
        }else {
            ToastUtils.showShort(bean.getDesc());
        }

    }
}
