package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;

import com.baidu.location.BDLocation;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.CheckAdapter;
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
    public static String PARAM2 = "address";
    private CheckAdapter adapter;
    private BDLocation location;
    private List<Site.Monitor> monitors;
    private String HashLength = "3";
    private int pageNum = 1;
    private int pageSize = 20;
    private int type = 0;
    private String address;

    public CheckFragment() {
    }

    public static CheckFragment newInstance(Parcelable par, int type, String address) {
        CheckFragment fragment = new CheckFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM, par);
        args.putInt(PARAM1, type);
        args.putString(PARAM2, address);
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
            address = getArguments().getString(PARAM2);
        }

        if (adapter == null) {
            adapter = new CheckAdapter(monitors, context);
            adapter.setOnLoadMoreListener(this);
            RecycleviewUtil.initLinearRecycleView(recyclerView, adapter, context);
        } else {
            adapter.updateData(monitors);
        }

//        if (type == 0)
        requestLocation();
    }

    private void requestLocation() {
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", Base64.encodeToString(String.valueOf(pageNum).getBytes(), Base64.DEFAULT));
        params.put("pageSize", Base64.encodeToString(String.valueOf(pageSize).getBytes(), Base64.DEFAULT));

        if (location != null) {
            params.put("filterName", Base64.encodeToString("getNearbySites".getBytes(), Base64.DEFAULT));
            params.put("lon", Base64.encodeToString(String.valueOf(location.getLongitude()).getBytes(), Base64.DEFAULT));
            params.put("lat", Base64.encodeToString(String.valueOf(location.getLatitude()).getBytes(), Base64.DEFAULT));
            params.put("geoHashLength", Base64.encodeToString(HashLength.getBytes(), Base64.DEFAULT));   //1-12
            //0,bg_ngo  1,妇幼保健 2，疾病预防控制中心  3，美沙酮门诊 4，性病中心或皮防所  5，其他机构
            params.put("siteType", Base64.encodeToString(String.valueOf(type).getBytes(), Base64.DEFAULT));
        } else if (!TextUtils.isEmpty(address)) {
            params.put("filterName", Base64.encodeToString("getSitesInRegion".getBytes(), Base64.DEFAULT));
            params.put("province", Base64.encodeToString(address.getBytes(), Base64.DEFAULT));
            params.put("siteType", Base64.encodeToString(String.valueOf(type).getBytes(), Base64.DEFAULT));
        } else {
            params.put("filterName", Base64.encodeToString("listSitesByFilter".getBytes(), Base64.DEFAULT));
            params.put("siteType", Base64.encodeToString(String.valueOf(type).getBytes(), Base64.DEFAULT));
        }
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
            monitors = DataHelper.fillCheckData(bean.getResult().getMonitoringSites());
            if (pageNum == 1)
                adapter.updateData(monitors);
            else
                adapter.addData(monitors);
        } else
            adapter.loadMoreEnd();
    }

    @Override
    public void OnLoadMore() {
        pageNum++;
        requestLocation();
    }
}
