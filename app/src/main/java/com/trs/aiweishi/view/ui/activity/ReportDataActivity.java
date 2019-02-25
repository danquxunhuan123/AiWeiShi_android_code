package com.trs.aiweishi.view.ui.activity;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.lf.http.presenter.IHomePresenter;
import com.lf.http.view.ICheckView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class ReportDataActivity extends BaseActivity implements ICheckView {
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.edit_content)
    EditText etContent;
    @BindView(R.id.et_contact)
    EditText etContact;

    @BindViews({R.id.cb_check_name, R.id.cb_check_address, R.id.cb_check_note,
            R.id.cb_check_phone, R.id.cb_check_time, R.id.cb_check_way})
    List<CheckBox> checkBoxes;

    private StringBuilder builder = new StringBuilder();

    public static final String MONITORING_SITE_NAME = "monitoring_site_name";

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected String initToolBarName() {
        return AppConstant.CHECK_JUDGE;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_report_data;
    }

    @Override
    protected void initData() {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CheckBox cb = ReportDataActivity.this.findViewById(buttonView.getId());
                    String s = cb.getText().toString();
                    if (isChecked) {
                        builder.append(s).append("、");
                    } else {
                        int strIndex = builder.indexOf(s);
                        builder.delete(strIndex, strIndex + s.length() + 1);
                    }
                }
            });
        }
    }


    public void report(View view) {
        try {
            String contact = etContact.getText().toString().trim();
            if (TextUtils.isEmpty(contact)) {
                ToastUtils.showShort("请填写联系电话");
                return;
            }

            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtils.showShort("请填写问题描述");
                return;
            }

            String errorField = builder.toString();
            if (TextUtils.isEmpty(errorField)) {
                ToastUtils.showShort("请选择错误类型");
                return;
            }

            Map<String, String> param = new HashMap<>();

            param.put("userName", Base64.encodeToString(contact.getBytes("UTF-8"), Base64.NO_WRAP));

            param.put("content", Base64.encodeToString(content.getBytes("UTF-8"), Base64.NO_WRAP)); //

            String monitoringSiteName = getIntent().getStringExtra(MONITORING_SITE_NAME);
            param.put("monitoringSiteName", Base64.encodeToString(monitoringSiteName.getBytes("UTF-8"), Base64.NO_WRAP));

            param.put("errorField", Base64.encodeToString(errorField.getBytes("UTF-8"), Base64.NO_WRAP));

            param.put("contact", Base64.encodeToString(contact.getBytes("UTF-8"), Base64.NO_WRAP));

            presenter.addCorrection(param);

            MProgressDialog.showProgress(this, config);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkSuccess(String result) {
        //        {"serviceName":"addCorrection","code":0,"desc":"检测点纠错成功！"}
        try {
            JSONObject json = new JSONObject(result);
            String desc = json.getString("desc");
            if (json.getInt("code") == 0) {
                ToastUtils.showShort(getResources().getString(R.string.check_judge_success));
                finish();
            } else {
                ToastUtils.showShort(desc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MProgressDialog.dismissProgress();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
