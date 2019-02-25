package com.trs.aiweishi.view.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.CheckSearchAdapter;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.SearchBean;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.util.SoftInputUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckSearchActivity extends BaseActivity implements BaseAdapter.OnLoadMoreListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<SearchBean.SearchData> list;
    private CheckSearchAdapter adapter;
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
        if (adapter == null) {
            adapter = new CheckSearchAdapter(list, this);
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

        StringBuilder searchWords = new StringBuilder().append("(");
        String search = etSearch.getText().toString().trim();
        for (int i = 0; i < search.length(); i++) {
            if (i != search.length() - 1)
                searchWords.append(search.charAt(i)).append(" and ");
            else
                searchWords.append(search.charAt(i)).append(")");
        }

        params.put("searchword", searchWords.toString());
        presenter.getSearchData(AppConstant.SEARCH_JCD, params);
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
