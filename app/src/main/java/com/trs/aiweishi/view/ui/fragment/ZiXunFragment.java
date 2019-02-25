package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ZiXunAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.lf.http.bean.ListData;
import com.lf.http.bean.ListDataBean;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.lf.http.view.IZiXunView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

public class ZiXunFragment extends BaseFragment implements IZiXunView
        , BaseAdapter.OnLoadMoreListener
        , ZiXunAdapter.OnZiXunItemClickListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    private String key = "iwsAppaa";
    private String transformation = "DES/ECB/pkcs5padding";
    protected static String mParam1 = "url";
    protected static String C_NAME = "cname";
    private ZiXunAdapter adapter;
    private List<ListData> list_datas;
    private ListDataBean dataBean;
    private String url;
    private int page = 1;

    public ZiXunFragment() {
    }

    public static ZiXunFragment newInstance(String param1, String cname) {
        ZiXunFragment fragment = new ZiXunFragment();
        Bundle args = new Bundle();
        args.putString(mParam1, param1);
        args.putString(C_NAME, cname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponent() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initData() {
        if (adapter == null) {
            adapter = new ZiXunAdapter(list_datas, context);  //DataHelper.getZiXunData()
            adapter.setOnLoadMoreListener(this);
            adapter.setOnZiXunItemClickListener(this);
            RecycleviewUtil.initLinearRecycleView(recyclerView, adapter, context);
        } else {
            adapter.updateData(list_datas);
        }

        if (getArguments() != null) {
            url = getArguments().getString(mParam1);
            adapter.setCname(getArguments().getString(C_NAME));
            presenter.getChannelData(url);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_zixun;
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        dataBean = (ListDataBean) baseBean;
        list_datas = dataBean.getList_datas();
        if (page == 0) {
            adapter.updateData(list_datas);
        } else {
            adapter.addData(list_datas);
        }
    }

    @Override
    public void OnLoadMore() {
        if ((dataBean.getNowPage() + 1) < dataBean.getCountPage()) {
            //当前页数小于总页数，加载更多
            if (page == 1)
                url = url.replace("documents", "documents" + "_" + page);
            else {
                String url_1 = url.substring(0, url.indexOf("documents_"));
                url = new StringBuilder(url_1)
                        .append("documents")
                        .append("_")
                        .append(page)
                        .append(".json")
                        .toString();
            }
            page++;
            presenter.getChannelData(url);
        } else {
            //没有更多数据
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void onZiXunItemClick(String docid) {
        try {
            byte[] bytes = EncryptUtils.encryptDES(
                    PhoneUtils.getIMEI().getBytes("gbk"),
                    key.getBytes(), transformation, null);

            Map<String, String> param = new HashMap<>();
            param.put("IMEI", PhoneUtils.getIMEI());
            param.put("token", Base64.encodeToString(bytes, Base64.NO_WRAP));
            param.put("docid", docid);
            presenter.clickToRead(AppConstant.CLICK_TO_READ, param);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void clickToRead(String result) {
//{"code":"204","msg":"该设备今天已点击阅读"}
    }
}
