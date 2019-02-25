package com.trs.aiweishi.view.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ChecksInfoAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.CheckPackegBean;
import com.lf.http.presenter.IUserPresenter;
import com.trs.aiweishi.util.RecycleviewUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCheckHistoryActivity extends BaseActivity {

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.rv_checks_info)
    RecyclerView rvChecks;

    public static final int TYPE = 1;

    @Override
    protected String initToolBarName() {
        return "检测包记录";
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
        param.put("mobile", spUtils.getString(AppConstant.USER_PHONE));
        presenter.getCheckHistory(AppConstant.CHECK_HISTORY_RESULT, param);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        CheckPackegBean result = (CheckPackegBean) baseBean;
        ChecksInfoAdapter adapter = new ChecksInfoAdapter(result.getData(), this);
        adapter.setTypeCheckHistory(TYPE);
        RecycleviewUtil.initLinearRecycleView(rvChecks, adapter, this);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
