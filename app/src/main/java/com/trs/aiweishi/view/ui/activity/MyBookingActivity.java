package com.trs.aiweishi.view.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.YuYueAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.bean.YuYueItem;
import com.lf.http.presenter.IUserPresenter;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.lf.http.view.IBookView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBookingActivity extends BaseActivity implements IBookView {

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.ll_yuyue)
    LinearLayout linearLayout;
    @BindView(R.id.tv_yuyue_name)
    TextView tvCheckName;
    @BindView(R.id.tv_yuyue_time)
    TextView tvCheckTime;
    @BindView(R.id.but_yuyue)
    Button btnYuYue;
    @BindView(R.id.ll_warn_yuyue)
    LinearLayout warnYuYue;
    @BindView(R.id.ll_history_yuyue)
    LinearLayout llHistory;
    @BindView(R.id.recycleview_yuyue)
    RecyclerView recycleviewYuyue;

    private List<YuYueItem> historList;
    private String bookId;
    private boolean isNoYuYue = true;

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
        Map<String, String> param = new HashMap<>();
        param.put("bookingId", bookId);
        presenter.cancleBook(AppConstant.CANCLE_BOOKED, param);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getUnBooked(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            if (obj.getInt("code") == 0) {
                isNoYuYue = false;
                JSONObject data = obj.getJSONObject("data");
                linearLayout.setVisibility(View.VISIBLE);
                tvCheckName.setText(data.getString("monitoringPoint"));
                tvCheckTime.setText(data.getString("bookingTime"));
                bookId = data.getString("bookingId");
            }

            Map<String, String> param = new HashMap<>();
            param.put("mobile", spUtils.getString(AppConstant.USER_PHONE));
            presenter.getHistoryBooked(AppConstant.GET_HISTORY_BOOKED, param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getHistoryBooked(String result) {
        try {
            historList = new ArrayList<>();
            JSONObject obj = new JSONObject(result);
            if (obj.getInt("code") == 0 && obj.getInt("count") != 0) {
                isNoYuYue = false;
                llHistory.setVisibility(View.VISIBLE);
                JSONArray data = obj.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject yuyue = (JSONObject) data.get(i);
                    YuYueItem item = new YuYueItem();
                    item.setBookingId(yuyue.getString("bookingId"));
                    item.setMonitoringPoint(yuyue.getString("monitoringPoint"));
                    item.setBookingTime(yuyue.getString("bookingTime"));
                    historList.add(item);
                }
                YuYueAdapter adapter = new YuYueAdapter(historList, this);
                RecycleviewUtil.initLinearRecycleView(recycleviewYuyue, adapter, this);
            }

            if (isNoYuYue)
                warnYuYue.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancleBooking(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            if (obj.getInt("code") == 0) {
                btnYuYue.setClickable(false);
                btnYuYue.setText("已取消");
                btnYuYue.setBackground(getResources().getDrawable(R.drawable.gray_corner_shape));
                btnYuYue.setTextColor(getResources().getColor(R.color.color_75787b));
            }
            ToastUtils.showShort(obj.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
