package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;

import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.LogUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.CheckAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.Site;
import com.trs.aiweishi.bean.SiteBean;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.DataHelper;
import com.trs.aiweishi.util.RecycleviewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

public class CheckFragment extends BaseFragment implements BaseAdapter.OnLoadMoreListener {
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    public static String PARAM = "parce";
    public static String PARAM1 = "siteType";
    private CheckAdapter adapter;
    private BDLocation location;
    private List<Site.Monitor> monitors;
    private String HashLength = "2";
    private int pageNum = 1;
    private int pageSize = 20;
    private int type = 0;

    public CheckFragment() {
    }

    public static CheckFragment newInstance(Parcelable par, int type) {
        CheckFragment fragment = new CheckFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM, par);
        args.putInt(PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponent() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            location = getArguments().getParcelable(PARAM);
            type = getArguments().getInt(PARAM1);
        }

        if (adapter == null) {
            adapter = new CheckAdapter(monitors, context);
            adapter.setOnLoadMoreListener(this);
            RecycleviewUtil.initLinearRecycleView(recyclerView, adapter, context);
        } else {
            adapter.updateData(monitors);
        }
        requestLocation();
    }

    private void requestLocation() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", Base64.encodeToString(String.valueOf(pageNum).getBytes(), Base64.DEFAULT));
        params.put("pageSize", Base64.encodeToString(String.valueOf(pageSize).getBytes(), Base64.DEFAULT));

        if (!TextUtils.isEmpty(location.getCity())){
            //getNearbySites
            params.put("filterName", Base64.encodeToString("getNearbySites".getBytes(), Base64.DEFAULT));
            params.put("lon", Base64.encodeToString(String.valueOf(location.getLongitude()).getBytes(), Base64.DEFAULT));
            params.put("lat", Base64.encodeToString(String.valueOf(location.getLatitude()).getBytes(), Base64.DEFAULT));
            params.put("geoHashLength", Base64.encodeToString(HashLength.getBytes(), Base64.DEFAULT));   //1-12
            //0,bg_ngo  1,妇幼保健 2，疾病预防控制中心  3，美沙酮门诊 4，性病中心或皮防所  5，其他机构
            params.put("siteType", Base64.encodeToString(String.valueOf(type).getBytes(), Base64.DEFAULT));
        } else{
            //listSitesByFilter
            params.put("filterName", Base64.encodeToString("listSitesByFilter".getBytes(), Base64.DEFAULT));
            params.put("siteType", Base64.encodeToString(String.valueOf(type).getBytes(), Base64.DEFAULT));
        }

        //getSitesInRegion
//        String province = split[0]+split[1]+split[2];
//        params.put("province", Base64.encodeToString(province.getBytes(), Base64.DEFAULT));  //"安徽省"
//        params.put("city", Base64.encodeToString(split[1].getBytes(), Base64.DEFAULT));  //"合肥市"
//        params.put("country", Base64.encodeToString(split[2].getBytes(), Base64.DEFAULT)); //庐阳区  瑶海区

        presenter.getLocationData(params);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_check;
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        SiteBean bean = (SiteBean) baseBean;
        if (bean.getResult() != null) {
            List<Site.Monitor> monitors = DataHelper.fillCheckData(bean.getResult().getMonitoringSites());
            if (pageNum == 1)
                adapter.updateData(monitors);
            else
                adapter.addData(monitors);
        }

    }

    @Override
    public void OnLoadMore() {
        pageNum++;
        requestLocation();
    }
}
