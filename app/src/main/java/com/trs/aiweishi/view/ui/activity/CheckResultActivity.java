package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.view.IRegistView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckResultActivity extends BaseActivity  implements IRegistView {
    @Inject
    IUserPresenter presenter;
    @BindView(R.id.et_get_phone)
    EditText etPhone;
    @BindView(R.id.et_get_yanzhengma)
    EditText etYanzhengma;
    @BindView(R.id.tv_get_check_result)
    TextView getCheckResult;

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_check_result;
    }

    public void getCode(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("attributeName", Base64.encodeToString("mobile".getBytes(), Base64.DEFAULT));
        params.put("attributeValue", Base64.encodeToString(etPhone.getText().toString().trim().getBytes(), Base64.DEFAULT));
        presenter.getCode(0,params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 0){
            ToastUtils.showShort(baseBean.getDesc());
        }else{
            ToastUtils.showShort(baseBean.getDesc());
        }
    }

    public void nextRegist(View view) {
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

    @Override
    public void notifyCodeSuccess(BaseBean bean) {
        if (bean.getCode() == 0){
            Intent intent = new Intent(this,CheckInfosActivity.class);
            intent.putExtra(CheckInfosActivity.USER_PHONE,etPhone.getText().toString().trim());
            startActivity(intent);
        }else {
            ToastUtils.showShort(bean.getDesc());
        }
    }

    @Override
    protected void initData() {
        getCheckResult.setText(getResources().getString(R.string.check_result_search
                ,"")); //spUtils.getString(AppConstant.USER_NAME)
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
