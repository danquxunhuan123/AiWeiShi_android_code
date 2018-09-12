package com.trs.aiweishi.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.WorkTimeAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.bean.Site;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.AlertDialogUtil;
import com.trs.aiweishi.util.PopWindowUtil;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.view.ITimeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckDetailActivity extends BaseActivity implements ITimeView, WorkTimeAdapter.OnTimeSelectListener {

    @Inject
    IHomePresenter presenter;

    @BindView(R.id.ll_content)
    LinearLayout content;
    @BindView(R.id.iv_no_time_show)
    ImageView showNo;
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
    @BindView(R.id.tv_yuyue_time)
    TextView yuyueTime;
    @BindView(R.id.ll_weekday)
    LinearLayout weekDay;
    @BindView(R.id.ll_monthday)
    LinearLayout monthDay;
    @BindView(R.id.recycleview_time)
    RecyclerView recycleTime;

    public static String TAG = "bean";
    public static String INTEXTRA = "extra";
    private String lat = "";
    private String lon = "";
    private String title = "";
    private TextView selectTextView;
    private int selectIndex = 0;
    private List<String> weeks = new ArrayList<>();
    private List<String> days = new ArrayList<>();
    private String selectTimes = "";
    private String ngoId;
    private String ngoName;

    @Override
    protected String initToolBarName() {
        return "爱检测";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        int extra = getIntent().getIntExtra(INTEXTRA, 0);

//        String id;
        if (extra == 0) {
            Site.Monitor bean = (Site.Monitor) getIntent().getSerializableExtra(TAG);
            tvName.setText(bean.getOrgName());
            tvAddress.setText(Html.fromHtml(bean.getOrgAddr()));
            tvPhone.setText(bean.getTel());
            tvCheckWay.setText(bean.getRemark());
            tvCheckNote.setText("暂无");
//            id = bean.getId();

            lat = bean.getLat();
            lon = bean.getLon();
            title = String.valueOf(Html.fromHtml(bean.getOrgAddr()));
        } else {
            SearchBean.SearchData searBean = getIntent().getParcelableExtra(TAG);
            tvName.setText(searBean.getORGNAME());
            tvAddress.setText(Html.fromHtml(searBean.getORGADDR()));
            tvPhone.setText(searBean.getTEL());
            tvCheckWay.setText("暂无");
            tvCheckNote.setText("暂无");
//            id = searBean.getID();

            lat = searBean.getLAT();
            lon = searBean.getLON();
            title = String.valueOf(Html.fromHtml(searBean.getORGNAME()));
        }

        //初始化检测时间
        setTime("", "", "");
        presenter.getNgoInfo(AppConstant.GET_NGO_INFO);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_check_detail;
    }

    @OnClick({R.id.iv_back, R.id.tv_phone, R.id.tv_address, R.id.tv_yuyue_sure})
    public void back(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_address:
                openMap();
                break;
            case R.id.tv_phone:
                callPhone();
                break;
            case R.id.tv_yuyue_sure:
                showYuYueTime();
                break;
        }
    }

    private void callPhone() {
        final String[] phones = tvPhone.getText().toString().split(",");
        if (phones.length != 1) {
            new PopWindowUtil(this)
                    .setContentView(R.layout.pop_bottom_layout)
                    .setContent(R.id.tv_select_1, phones[0])
                    .setContent(R.id.tv_select_2, phones[1])
                    .getView(R.id.tv_select_1, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PhoneUtils.dial(phones[0]);
                        }
                    })
                    .getView(R.id.tv_select_2, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PhoneUtils.dial(phones[1]);
                        }
                    })
                    .showAtLocation(content);
        } else
            PhoneUtils.dial(phones[0]);
    }

    private void showYuYueTime() {
        new AlertDialogUtil(this)
                .setDialogView()
                .setDialogTitle("确定预约时间")
                .setDialogContent("您确定要预约该监测点 " + yuyueTime.getText().toString() + " 时间段吗？请按时到场哦~")
                .setOnClickListener(new AlertDialogUtil.OnClickListener() {
                    @Override
                    public void OnSureClick() {
                        submitBooking();
                    }

                    @Override
                    public void OnCancleClick() {

                    }
                })
                .create();
    }


    private void submitBooking() {
        String[] split = yuyueTime.getText().toString().split(" ");
        String bookingTime = split[0] + " " + split[2].split("-")[0];
        Map<String, String> param = new HashMap<>();
        param.put("mobile", spUtils.getString(AppConstant.USER_PHONE));
        param.put("nickName", spUtils.getString(AppConstant.USER_NAME));
        param.put("way", "0");
        param.put("bookingTime", bookingTime);
        param.put("jiancedian", ngoId);
        param.put("monitoringPoint", ngoName);
        param.put("remarks", "");
        presenter.submitBooking(AppConstant.SUBMIT_BOOKING, param);
    }

    public void openMap() {
        //如果已安装,
        if (isAvilible(this, "com.baidu.BaiduMap")) {//传入指定应用包名
//            ToastUtils.showShort("即将打开百度地图");
            Uri mUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + title);
            Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
            startActivity(mIntent);
        } else if (isAvilible(this, "com.autonavi.minimap")) {
//            ToastUtils.showShort("即将打开高德地图");
            Uri mUri = Uri.parse("geo:" + lat + "," + lon + "?q=" + title);
            Intent intent = new Intent("android.intent.action.VIEW", mUri);
            startActivity(intent);
        } else {
            ToastUtils.showShort("请安装第三方地图方可导航");
        }
    }

    //检查手机上是否安装了指定的软件
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public void setTime(String day, String week, String time) {
        yuyueTime.setText(String.format(getResources().getString(R.string.yuyue_time)
                , day, week, time));
    }

    @Override
    public void getYuYueTime(String json) {
        monthDay.removeAllViews();
        weekDay.removeAllViews();

        //解析数据封装map
        final Map<String, List<String>> dataMap = new LinkedHashMap<>();
        try {
            JSONObject data = new JSONObject(json).getJSONObject("data");
            JSONArray worktime = data.getJSONArray("worktime");
            for (int i = 0; i < worktime.length(); i++) {
                String day = worktime.getJSONObject(i).keys().next();
                List<String> times = new ArrayList<>();
                JSONArray arr = worktime.getJSONObject(i).getJSONArray(day);
                for (int j = 0; j < arr.length(); j++) {
                    times.add(arr.getString(j));
                }
                dataMap.put(day, times);
            }

            Iterator<Map.Entry<String, List<String>>> iterator = dataMap.entrySet().iterator();

            //初始化星期和日期
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> next = iterator.next();
                String keyDay = next.getKey();
                days.add(keyDay);

                String chineseWeek = TimeUtils.getChineseWeek(keyDay, new SimpleDateFormat("yyyy-MM-dd"));
                weeks.add(chineseWeek);

                //初始化星期布局
                TextView weekTextView = new TextView(this);
                weekTextView.setGravity(Gravity.CENTER);
                weekTextView.setText(chineseWeek.substring(1));
                weekTextView.setTextSize(14);
                weekTextView.setTextColor(getResources().getColor(R.color.color_75787b));
                LinearLayout.LayoutParams para1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                para1.weight = 1;
                weekTextView.setLayoutParams(para1);
                weekDay.addView(weekTextView);

                //初始化日期布局
                View dayView = getLayoutInflater().inflate(R.layout.day_time_layout, null);
                LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                para.weight = 1;
                dayView.setLayoutParams(para);

                TextView tvMonth = dayView.findViewById(R.id.tv_yuyue_month);
                tvMonth.setText((Calendar.getInstance().get(Calendar.MONTH) + 1) + "月"); //
                TextView tvDay = dayView.findViewById(R.id.tv_yuyue_day);
                String strDay = keyDay.substring(keyDay.length() - 2, keyDay.length());
                tvDay.setText(strDay);

                if ("01".equals(strDay))
                    tvMonth.setVisibility(View.VISIBLE);

//                final TextView dayTextView = new TextView(this);
//                dayTextView.setGravity(Gravity.CENTER);
//                dayTextView.setText(keyDay.substring(keyDay.length() - 2, keyDay.length()));
//                dayTextView.setTextSize(14);
//                dayTextView.setTextColor(getResources().getColor(R.color.color_75787b));
//                LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(SizeUtils.dp2px(50), SizeUtils.dp2px(50));
//                para.weight = 1;
//                dayTextView.setLayoutParams(para);
                monthDay.addView(dayView);
            }

            //初始化时间段
            List<String> value = dataMap.entrySet().iterator().next().getValue();
            if (value.size() == 0)
                showNo.setVisibility(View.VISIBLE);
            final WorkTimeAdapter adapter = new WorkTimeAdapter(value, this);
            adapter.setOnTimeSelectListener(this);
            RecycleviewUtil.initGridNoTypeRecycleView(recycleTime, adapter, this, 3);

            //默认选中第一天
            TextView tv = monthDay.getChildAt(0).findViewById(R.id.tv_yuyue_day);
            tv.setTextColor(Color.WHITE);
            tv.setBackground(getResources().getDrawable(R.drawable.bg_blue_drawable));
            selectTextView = tv;
            //点击日期事件处理
            for (int i = 0; i < monthDay.getChildCount(); i++) {
                final TextView dayTextView = monthDay.getChildAt(i).findViewById(R.id.tv_yuyue_day);
                final int finalI = i;
                dayTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectIndex = finalI;
                        selectTimes = "";
                        setTime(days.get(selectIndex), weeks.get(selectIndex), selectTimes);

                        if (selectTextView != null) {
                            selectTextView.setTextColor(getResources().getColor(R.color.color_75787b));
                            selectTextView.setBackground(null);
                        }
                        dayTextView.setTextColor(Color.WHITE);
                        dayTextView.setBackground(getResources().getDrawable(R.drawable.bg_blue_drawable));
                        selectTextView = dayTextView;

                        //刷新时间段
                        List<String> list = dataMap.get(days.get(selectIndex));
                        adapter.updateData(list);
                        if (list.size() == 0)
                            showNo.setVisibility(View.VISIBLE);
                        else
                            showNo.setVisibility(View.GONE);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getNgoInfo(String json) {
        JSONObject data = null;
        try {
            data = new JSONObject(json).getJSONArray("data").getJSONObject(0);
            ngoId = data.getString("NGOid");
            ngoName = data.getString("NGOName");
            Map<String, String> param = new HashMap<>();
            param.put("monitoringPointId", ngoId);
            presenter.getYuYueTime(AppConstant.FIND_NGO_BYID, param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void submitBooking(String string) {
        try {
            JSONObject result = new JSONObject(string);
//            int code = result.getInt("code");
            ToastUtils.showShort(result.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTimeSelect(CharSequence text) {
        selectTimes = text.toString();
        setTime(days.get(selectIndex), weeks.get(selectIndex), text.toString());
    }
}
