package com.trs.aiweishi.view.ui.activity;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.bean.Site;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckDetailActivity extends BaseActivity{

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_check_way)
    TextView tvCheckWay;
    @BindView(R.id.tv_check_note)
    TextView tvCheckNote;

    public static String TAG = "bean";
    public static String INTEXTRA = "extra";
    @Override
    protected String initToolBarName() {
        return "快乐检";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {

        int extra =  getIntent().getIntExtra(INTEXTRA,0);

        if (extra == 0){
            Site.Monitor bean = (Site.Monitor) getIntent().getSerializableExtra(TAG);
            tvName.setText(bean.getOrgName());
            tvAddress.setText(Html.fromHtml(bean.getOrgAddr()));
            tvPhone.setText(bean.getTel());
            tvCheckWay.setText(bean.getRemark());
            tvCheckNote.setText("暂无");
        }else{
            SearchBean.SearchData searBean =  getIntent().getParcelableExtra(TAG);
            tvName.setText(searBean.getORGNAME());
            tvAddress.setText(Html.fromHtml(searBean.getORGADDR()));
            tvPhone.setText(searBean.getTEL());
            tvCheckWay.setText("暂无");
            tvCheckNote.setText("暂无");
        }

    }
    @Override
    public int initLayout() {
        return R.layout.activity_check_detail;
    }

    @OnClick({R.id.iv_back,R.id.tv_phone})
    public void back(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_phone:
                PhoneUtils.dial(tvPhone.getText().toString());
                break;
        }
    }

    @Override
    public void showSuccess(BaseBean baseBean) {

    }
}
