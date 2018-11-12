package com.trs.aiweishi.view.ui.activity;

import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;


    @Override
    protected String initToolBarName() {
        return "";
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initData() {
        tvVersion.setText(spUtils.getString(AppConstant.VERSION_NAME, AppUtils.getAppVersionName()) + "版");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
