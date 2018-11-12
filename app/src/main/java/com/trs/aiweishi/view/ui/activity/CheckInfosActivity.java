package com.trs.aiweishi.view.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ChecksInfoAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.CheckResult;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.util.RecycleviewUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckInfosActivity extends BaseActivity {

    public static String USER_PHONE = "user_phone";
    @Inject
    IUserPresenter presenter;
    @BindView(R.id.rv_checks_info)
    RecyclerView rvChecks;

    @Override
    protected String initToolBarName() {
        return "检测结果"; //spUtils.getString(AppConstant.USER_NAME+"的检测结果","检测结果")
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_check_infos;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("mobile",getIntent().getExtras().getString(USER_PHONE));
        presenter.getCheckInfo(AppConstant.CHECK_RESULT,param);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        CheckResult result = (CheckResult) baseBean;
        ChecksInfoAdapter adapter = new ChecksInfoAdapter(result.getData(),this);
        RecycleviewUtil.initLinearRecycleView(rvChecks,adapter,this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
