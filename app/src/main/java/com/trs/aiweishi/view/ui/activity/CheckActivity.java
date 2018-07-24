package com.trs.aiweishi.view.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.FragmentAdapter;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.JsonBean;
import com.trs.aiweishi.location.LocationHelper;
import com.trs.aiweishi.util.DataHelper;
import com.trs.aiweishi.util.GetJsonDataUtil;
import com.trs.aiweishi.view.ui.fragment.CheckFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckActivity extends BaseActivity implements
        LocationHelper.OnLocationListener {

    @Inject
    LocationHelper locationHelper;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_check)
    ViewPager viewPager;

//    private BDLocation location;
    private List<String> titles = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private FragmentAdapter adapter;
    private boolean isLoaded = false;
    private Thread thread;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    public static final int MSG_LOAD_FAILED = 0x0003;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        locationHelper.startLocation();

        titles.add("疾控中心");   //2
        titles.add("妇幼保健");   //1
        titles.add("NGO机构");    //0
        titles.add("皮肤性病");   //4
        titles.add("美沙酮点");   //3
        titles.add("其他机构");   //5
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private void initJsonData() {//解析数据
        parseJson();
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }


    @Override
    protected String initToolBarName() {
        return "快乐检";
    }

    @Override
    protected void initListener() {
        locationHelper.setOnLocationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationHelper.stopLocation();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_check;
    }

    @OnClick({R.id.iv_back, R.id.iv_edit_location, R.id.tv_search_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_location:
                Intent intent = new Intent(this, CheckSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_edit_location:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(CheckActivity.this, getResources().getString(R.string.parse_location_desc), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + "," +
                        options2Items.get(options1).get(options2) + "," +
                        options3Items.get(options1).get(options2).get(options3);

                tvLocation.setText(tx);

//                Address address  = new Address.Builder()
//                        .city(options1Items.get(options1).getPickerViewText())
//                        .district(options2Items.get(options1).get(options2))
//                        .street(options3Items.get(options1).get(options2).get(options3))
//                        .build();
//                location.setAddr(address);
//
//                fragments.clear();
//
//                for (int a =0;a < titles.size();a ++){
//                    fragments.add(CheckFragment.newInstance(location,a));
//                }
//                adapter.update(fragments);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    @Override
    public void onLocation(BDLocation location) {
//        this.location = location;
        String addr = location.getCity() +
                "," + location.getDistrict() +
                "," + location.getAddrStr();
        tvLocation.setText(addr);

//        for (int a = 0; a < titles.size(); a++) {
//            fragments.add(CheckFragment.newInstance(location, a));
//        }
            fragments.add(CheckFragment.newInstance(location, 2));
            fragments.add(CheckFragment.newInstance(location, 1));
            fragments.add(CheckFragment.newInstance(location, 0));
            fragments.add(CheckFragment.newInstance(location, 4));
            fragments.add(CheckFragment.newInstance(location, 3));
            fragments.add(CheckFragment.newInstance(location, 5));

        if (adapter == null) {
            adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(titles.size());
            tabLayout.setupWithViewPager(viewPager);
        } else {
            adapter.update(fragments);
        }
    }

    public void parseJson() {
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    private ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(CheckActivity.MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public void showSuccess(BaseBean baseBean) {

    }
}
