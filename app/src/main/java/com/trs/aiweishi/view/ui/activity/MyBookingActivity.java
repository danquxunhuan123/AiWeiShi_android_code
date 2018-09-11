package com.trs.aiweishi.view.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.YuYueAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.bean.YuYueItem;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.view.IBookView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBookingActivity extends BaseActivity implements IBookView{

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.tv_check_name)
    TextView tvCheckName;
    @BindView(R.id.tv_check_address)
    TextView tvCheckAddress;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    @BindView(R.id.recycleview_yuyue)
    RecyclerView recycleviewYuyue;

    private List<YuYueItem> historList;

    @Override
    protected String initToolBarName() {
        return "我的预约";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_my_booking;
    }

    @Override
    protected void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("mobile", spUtils.getString(AppConstant.USER_PHONE));
        presenter.getUnBooked(AppConstant.GET_BOOKED, param);
    }

    public void cancleYuYue(View view) {
        Map<String,String> param = new HashMap<>();
//        param.put("bookingId",ngoId);
        presenter.cancleBook(AppConstant.CANCLE_BOOKED,param);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getUnBooked(String obj) {


        Map<String, String> param = new HashMap<>();
        param.put("mobile", spUtils.getString(AppConstant.USER_PHONE));
        presenter.getHistoryBooked(AppConstant.GET_HISTORY_BOOKED, param);
    }

    @Override
    public void getHistoryBooked(String obj) {
        YuYueAdapter adapter = new YuYueAdapter(historList,this);
        RecycleviewUtil.initLinearRecycleView(recycleviewYuyue,adapter,this);
    }

    @Override
    public void cancleBooking(String obj) {

    }
}
