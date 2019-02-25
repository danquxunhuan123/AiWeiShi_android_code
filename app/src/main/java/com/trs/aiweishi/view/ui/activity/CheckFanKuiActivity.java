package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.view.View;

import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.lf.http.presenter.IHomePresenter;

import javax.inject.Inject;

import butterknife.OnClick;

import static com.trs.aiweishi.view.ui.activity.ReportDataActivity.MONITORING_SITE_NAME;

public class CheckFanKuiActivity extends BaseActivity {

    @Inject
    IHomePresenter presenter;

    @Override
    protected String initToolBarName() {
        return "用户反馈";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_check_fan_kui;
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.tv_report_data, R.id.tv_add_check_info, R.id.tv_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_report_data:
                Intent report = new Intent(this, ReportDataActivity.class);
                report.putExtra(MONITORING_SITE_NAME, getIntent().getStringExtra(MONITORING_SITE_NAME));
                startActivity(report);
                break;
            case R.id.tv_add_check_info:
                Intent add = new Intent(this, CheckAddActivity.class);
//                add.putExtra(LOCATION_DATA, getIntent().getParcelableExtra(LOCATION_DATA));
                startActivity(add);
                break;
            case R.id.tv_feedback:
                if (spUtils.getBoolean(AppConstant.IS_LOGIN)) {
                    startActivity(new Intent(this, FeedBackActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
