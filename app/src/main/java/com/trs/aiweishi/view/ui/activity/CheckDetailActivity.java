package com.trs.aiweishi.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.WorkTimeAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.lf.http.bean.SearchBean;
import com.lf.http.bean.Site;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.AlertDialogUtil;
import com.trs.aiweishi.util.PopWindowUtil;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.lf.http.view.ITimeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.trs.aiweishi.view.ui.activity.ReportDataActivity.MONITORING_SITE_NAME;

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
    @BindView(R.id.rl_yuyue_time)
    RelativeLayout yyTime1;
    @BindView(R.id.rl_yy_time)
    RelativeLayout yyTime2;
    @BindView(R.id.tv_yy_time)
    TextView yyTime3;
    @BindView(R.id.iv_suspension)
    ImageView suspension;

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
    private String orgId;
    private String orgName;
    private String orgAddr;
    private int downX;
    private int downY;
    private int l;
    private int t;

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
        int extra = getIntent().getIntExtra(INTEXTRA, 0);

        String reservable = "0";
        if (extra == 0) {
            Site.Monitor bean = getIntent().getParcelableExtra(TAG);
            orgName = bean.getOrgName();
            orgAddr = bean.getOrgAddr();
            orgId = bean.getOrgId();

            tvName.setText(Html.fromHtml(orgName));
            tvAddress.setText(Html.fromHtml(orgAddr));
            tvPhone.setText(bean.getTel());

            if (TextUtils.isEmpty(bean.getDetectionWay()))
                tvCheckWay.setText("暂无");
            else
                tvCheckWay.setText(bean.getDetectionWay());

            if (TextUtils.isEmpty(bean.getDescription()))
                tvCheckNote.setText("暂无");
            else
                tvCheckNote.setText(bean.getDescription());

            reservable = bean.getReservable();

            lat = bean.getLat();
            lon = bean.getLon();
            title = String.valueOf(Html.fromHtml(orgAddr));
        } else {
            SearchBean.SearchData searBean = getIntent().getParcelableExtra(TAG);
            tvName.setText(Html.fromHtml(searBean.getORGNAME()));
            tvAddress.setText(Html.fromHtml(searBean.getORGADDR()));
            tvPhone.setText(searBean.getTEL());
            tvCheckWay.setText("暂无");
            tvCheckNote.setText("暂无");
//            orgName = searBean.getORGNAME();
            orgId = searBean.getID();

            lat = searBean.getLAT();
            lon = searBean.getLON();
            title = String.valueOf(Html.fromHtml(searBean.getORGNAME()));
        }

        if ("1".equals(reservable)) {  //可预约
            yyTime1.setVisibility(View.VISIBLE);
            yyTime2.setVisibility(View.VISIBLE);
            yyTime3.setVisibility(View.VISIBLE);

            //初始化检测时间
            setTime("", "", "");
//        presenter.getNgoInfo(AppConstant.GET_NGO_INFO);

            Map<String, String> param = new HashMap<>();
            param.put("monitoringPointId", orgId);
            presenter.getYuYueTime(AppConstant.FIND_NGO_BYID, param);

//            RelativeLayout.LayoutParams pa = (RelativeLayout.LayoutParams) suspension.getLayoutParams();
//            int l = ScreenUtils.getScreenWidth() - suspension.getWidth() + SizeUtils.dp2px(10);
//            int t = ScreenUtils.getScreenHeight() - yyTime1.getHeight() + SizeUtils.dp2px(10);
//            pa.leftMargin = l;
//            pa.topMargin = t;
//            suspension.setLayoutParams(pa);
            suspension.setVisibility(View.VISIBLE);
            suspension.setOnTouchListener(shopCarSettleTouch);
        }
    }

    private View.OnTouchListener shopCarSettleTouch = new View.OnTouchListener() {
        int lastX, lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int ea = event.getAction();
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
//            int screenHeight = dm.heightPixels - 100;//需要减掉图片的高度
            int screenHeight = dm.heightPixels;//需要减掉图片的高度
            switch (ea) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();//获取触摸事件触摸位置的原始X坐标
                    lastY = (int) event.getRawY();
                    downX = lastX;
                    downY = lastY;
                    return false;
                case MotionEvent.ACTION_MOVE:
                    //event.getRawX();获得移动的位置
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    l = v.getLeft() + dx;
                    t = v.getTop() + dy;
                    int r = v.getRight() + dx;
                    int b = v.getBottom() + dy;

                    //下面判断移动是否超出屏幕
                    if (l < 0) {
                        l = 0;
                        r = l + v.getWidth();
                    }
//                    if (t < 0) { //屏幕顶部
//                        t = 0;
//                        b = t + v.getHeight();
//                    }
                    if (t < BarUtils.getStatusBarHeight()) { //以状态栏高度为界限
                        t = BarUtils.getStatusBarHeight();
                        b = t + v.getHeight();
                    }
                    if (r > screenWidth) {
                        r = screenWidth;
                        l = r - v.getWidth();
                    }
                    if (b > screenHeight) {
                        b = screenHeight;
                        t = b - v.getHeight();
                    }
                    v.layout(l, t, r, b);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
//                    v.postInvalidate();
                    return true;
                case MotionEvent.ACTION_UP:
                    // 解决当父布局发生改变冲毁则移动的view会回到原来的位置
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.removeRule(RelativeLayout.ABOVE);
                    params.setMargins(l, t - BarUtils.getStatusBarHeight(), 0, 0);
                    v.setLayoutParams(params);

                    return (Math.abs(event.getRawX() - downX) > 3 || Math.abs(event.getRawY() - downY) > 3)
                            ? true : false;
            }
            return false;
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        suspension.layout(upLeft, upTop, upRight, upBottom);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_check_detail;
    }

    @OnClick({R.id.iv_back, R.id.tv_phone, R.id.tv_address, R.id.tv_yuyue_sure
            , R.id.iv_suspension, R.id.tv_fankui}) //
    public void back(View view) {
        switch (view.getId()) {
            case R.id.tv_fankui:
                Intent fankui = new Intent(this, CheckFanKuiActivity.class);
                fankui.putExtra(MONITORING_SITE_NAME, orgName);
//                fankui.putExtra(LOCATION_DATA, getIntent().getParcelableExtra(LOCATION_DATA));
                startActivity(fankui);
                break;
            case R.id.iv_suspension:
                if (spUtils.getBoolean(AppConstant.IS_LOGIN)) {
//                    showCheckDialog();
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra(DetailActivity.TYPE, DetailActivity.CHECK_APPLAY_TYPE);
                    intent.putExtra(DetailActivity.URL, AppConstant.APPLAY_CHECK_PACKAGE + orgId);
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(getResources().getString(R.string.login_warn));
                    startActivity(new Intent(CheckDetailActivity.this, LoginActivity.class));
                }
                break;
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
                if (!TextUtils.isEmpty(yuyueTime.getText().toString().trim())) {
                    if (yuyueTime.getText().toString().split(" ").length == 3)
                        showYuYueTime();
                    else
                        ToastUtils.showShort(getResources().getString(R.string.check_time_warn));
                } else
                    ToastUtils.showShort(getResources().getString(R.string.check_time_warn));
                break;
        }
    }

//    private void showCheckDialog() {
//        final AlertDialogUtil dialogUtil = new AlertDialogUtil(this);
//        dialogUtil.setContentView(R.layout.check_dialog_layout)
//                .setViewClickListener(R.id.dialog_btn_sure, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogUtil.dismiss();
//                        EditText viewText = (EditText) dialogUtil.getViewText(R.id.dialog_content);
//                        submitPack(viewText.getText().toString().trim());
//                    }
//                })
//                .setViewClickListener(R.id.dialog_btn_cancel
//                        , new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialogUtil.dismiss();
//                            }
//                        })
//                .create();
//    }

    private void submitPack(String sendAddress) {
        if (TextUtils.isEmpty(sendAddress)) {
            ToastUtils.showShort("请输入地址！");
            return;
        }
        if (!TextUtils.isEmpty(yuyueTime.getText().toString().trim())
                && yuyueTime.getText().toString().split(" ").length == 3) {
            String[] split = yuyueTime.getText().toString().split(" ");
            String bookingTime = split[0] + " " + split[2].split("-")[0];

            Map<String, String> param = new HashMap<>();
            param.put("mobile", spUtils.getString(AppConstant.USER_PHONE));
            if (!TextUtils.isEmpty(spUtils.getString(AppConstant.USER_NAME)))
                param.put("nickName", spUtils.getString(AppConstant.USER_NAME));
            else
                param.put("nickName", spUtils.getString(AppConstant.USER_PHONE));
            param.put("monitoringPoint", orgName);
            param.put("way", "1");
            param.put("bookingTime", bookingTime);
            param.put("jiancedian", orgId);
            param.put("sendAddress", sendAddress);
            presenter.submitPackage(AppConstant.SUBMIT_PACKAGE, param);

            MProgressDialog.showProgress(this, config);
        } else
            ToastUtils.showShort(getResources().getString(R.string.check_time_warn));
    }

    @Override
    public void submitPackage(String string) {
        MProgressDialog.dismissProgress();

        try {
            JSONObject result = new JSONObject(string);
//            int code = result.getInt("code");
            ToastUtils.showShort(result.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
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
                        if (spUtils.getBoolean(AppConstant.IS_LOGIN)) {
                            submitBooking();
                        } else {
                            ToastUtils.showShort(getResources().getString(R.string.login_warn));
                            startActivity(new Intent(CheckDetailActivity.this, LoginActivity.class));
                        }
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
        if (!TextUtils.isEmpty(spUtils.getString(AppConstant.USER_NAME)))
            param.put("nickName", spUtils.getString(AppConstant.USER_NAME));
        else
            param.put("nickName", spUtils.getString(AppConstant.USER_PHONE));
        param.put("way", "0");
        param.put("bookingTime", bookingTime);
        param.put("jiancedian", orgId);
        param.put("monitoringPoint", orgName);
        param.put("remarks", "");
        presenter.submitBooking(AppConstant.SUBMIT_BOOKING, param);

        MProgressDialog.showProgress(this, config);
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
//                tvMonth.setText((Calendar.getInstance().get(Calendar.MONTH) + 1) + "月"); //
                String[] splitTime = keyDay.split("-");
                if ("01".equals(splitTime[2])) {
                    tvMonth.setVisibility(View.VISIBLE);
                    tvMonth.setText(splitTime[1] + "月");
                }

                TextView tvDay = dayView.findViewById(R.id.tv_yuyue_day);
                String strDay = keyDay.substring(keyDay.length() - 2, keyDay.length());
                tvDay.setText(strDay);
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
            orgId = data.getString("NGOid");
//            orgName = data.getString("NGOName");
            Map<String, String> param = new HashMap<>();
            param.put("monitoringPointId", orgId);
            presenter.getYuYueTime(AppConstant.FIND_NGO_BYID, param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void submitBooking(String string) {
        MProgressDialog.dismissProgress();

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
