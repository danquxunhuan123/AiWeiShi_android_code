package com.trs.aiweishi.view.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ListNgoAdapter;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.ListData;
import com.lf.http.bean.ListDataBean;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.RecycleviewUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class NgoListActivity extends BaseActivity implements BaseAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private String toolbarName;
    private List<ListData> listResult;
    private ListDataBean dataBean;
    private ListNgoAdapter adapter;
    private String url;
    private int page = 1;
    public static String PARAM1 = "url";
    public static String PARAM2 = "toolbar_name";

    @Override
    protected String initToolBarName() {
        toolbarName = getIntent().getStringExtra(PARAM2);
        return toolbarName;
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initListener() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_list_data;
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(PARAM1);

        if (adapter == null) {
            adapter = new ListNgoAdapter(listResult, this);
            adapter.setOnLoadMoreListener(this);
            RecycleviewUtil.initLinearRecycleView(recycleview, adapter, this);
        } else {
            adapter.updateData(listResult);
        }

        presenter.getChannelData(url);
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        if (refreshLayout != null)
            refreshLayout.setRefreshing(false);
        dataBean = (ListDataBean) baseBean;
        listResult = dataBean.getList_datas();
        if (page == 1) {
            adapter.updateData(listResult);
        } else {
            adapter.addData(listResult);
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

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getChannelData(url);
    }
}
