package com.trs.aiweishi.view.ui.activity;

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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {
    @Inject
    IUserPresenter userPresenter;

    @BindView(R.id.edit_feedback)
    EditText feedBack;

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_feed_back;
    }

    public void feedBack(View view) {
        String feed = feedBack.getText().toString().trim();
        if (TextUtils.isEmpty(feed)) {
            ToastUtils.showShort("内容不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userName", Base64.encodeToString(spUtils.getString(AppConstant.USER_PHONE).getBytes(), Base64.DEFAULT));
        params.put("content", Base64.encodeToString(feed.getBytes(), Base64.DEFAULT));
        userPresenter.feedBack(params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
//        if (baseBean.getCode() == 0)
        ToastUtils.showShort(baseBean.getDesc());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        finish();
    }
}
