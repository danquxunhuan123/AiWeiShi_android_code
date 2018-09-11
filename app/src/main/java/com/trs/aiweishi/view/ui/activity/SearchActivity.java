package com.trs.aiweishi.view.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.SearchAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.util.SoftInputUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements BaseAdapter.OnLoadMoreListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.swipeRefresh)
//    SwipeRefreshLayout swipeRefresh;

    private List<SearchBean.SearchData> list;
    private SearchAdapter adapter;
    private int page = 1;
    private int pagesize = 10;

    @Override
    protected String initToolBarName() {
        return "搜索";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
//        swipeRefresh.setOnRefreshListener(this);

        if (adapter == null) {
            adapter = new SearchAdapter(list, this);
            adapter.setOnLoadMoreListener(this);
            RecycleviewUtil.initLinearRecycleView(recyclerView, adapter, this);
        } else {
            adapter.updateData(list);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.iv_back, R.id.tv_click_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_click_search:
                page = 1;
                search();
                break;
        }
    }

    private void search() {
        SoftInputUtil.hideSoftInput(this, etSearch);
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pagesize", String.valueOf(pagesize));
        params.put("searchword", etSearch.getText().toString().trim());
//        params.put("docchannel","");

        presenter.getSearchData(AppConstant.SEARCH, params);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        list = ((SearchBean) baseBean).getDocData();
        if (page == 1)
            adapter.updateData(list);
        else
            adapter.addData(list);
    }

    @Override
    public void OnLoadMore() {
        page++;
        search();
    }
}
