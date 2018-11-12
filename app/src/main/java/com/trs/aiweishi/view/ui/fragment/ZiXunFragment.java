package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ZiXunAdapter;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.ListDataBean;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.view.IBaseView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ZiXunFragment extends BaseFragment implements BaseAdapter.OnLoadMoreListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    protected static String mParam1 = "url";
    protected static String C_NAME = "cname";
    private ZiXunAdapter adapter;
    private List<ListData> list_datas;
    private ListDataBean dataBean;
    private String url;
    private int page = 1;

    public ZiXunFragment() {
    }

    public static ZiXunFragment newInstance(String param1,String cname) {
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
            url = url.replace(".json", "_" + page + ".json");
            page++;
            presenter.getChannelData(url);
        } else {
            //没有更多数据
            adapter.loadMoreEnd();
        }
    }
}
