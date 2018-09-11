package com.trs.aiweishi.view.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ListDataAdapter;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.ListDataBean;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.RecycleviewUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ListDataActivity extends BaseActivity implements BaseAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private String toolbarName;
    private List<ListData> listResult;
    private ListDataBean dataBean;
    private ListDataAdapter adapter;
    private String url;
    private int page = 1;
    public static String PARAM1 = "url";
    public static String PARAM2 = "toolbar_name";

    @Override
    protected String initToolBarName() {
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
    protected void initData() {
        url = getIntent().getStringExtra(PARAM1);
        toolbarName = getIntent().getStringExtra(PARAM2);

        if (adapter == null) {
            adapter = new ListDataAdapter(listResult, this);
            adapter.setOnLoadMoreListener(this);
            RecycleviewUtil.initLinearRecycleView(recycleview, adapter, this);
        } else {
            adapter.updateData(listResult);
        }

        presenter.getChannelData(url);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_list_data;
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
