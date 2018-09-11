package com.trs.aiweishi.view.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.NgoAdapter;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.ListDataBean;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.DataHelper;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.view.INgoView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Liufan on 2018/5/17.
 */
public class NgoFragment extends BaseFragment implements INgoView
        , SwipeRefreshLayout.OnRefreshListener {
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.view_padding)
    View viewPadding;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    public static String mParam1;
    private List<ListData> list = new ArrayList<>();
    private NgoAdapter adapter;
    private final int SPAN_COUNT = 3;
    private int count_1 = 0;
    private int count_2 = 0;
    private List<ListData> channel_list;

    public NgoFragment() {

    }

    public static NgoFragment newInstance(ListData channelBean) {
        NgoFragment fragment = new NgoFragment();
        Bundle args = new Bundle();
        args.putParcelable(mParam1, channelBean);
//        args.putString(mParam2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponent() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initData() {
        LinearLayout.LayoutParams viewParam = (LinearLayout.LayoutParams) viewPadding.getLayoutParams();
        viewParam.height = BarUtils.getStatusBarHeight();
        viewPadding.setLayoutParams(viewParam);
        viewPadding.setBackgroundColor(getResources().getColor(R.color.color_e49850));

        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    BarUtils.setStatusBarVisibility(context,true);
//                }
//                if (newState == RecyclerView.SCROLL_STATE_SETTLING ){
//                    BarUtils.setStatusBarVisibility(context,false);
//                }
//            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                if (!recyclerView.canScrollVertically(-1)){
//                    if (!BarUtils.isStatusBarVisible(context))
//                        BarUtils.setStatusBarVisibility(context,true);
//                }
//                else{
//                    if (BarUtils.isStatusBarVisible(context))
//                        BarUtils.setStatusBarVisibility(context,false);
//                }
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
        ListData bean = null;
        if (getArguments() != null) {
            bean = getArguments().getParcelable(mParam1);
        }
        channel_list = bean.getChannel_list();
        presenter.getNgoData(0, channel_list.get(0).getUrl());
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_consult;
    }

    @Override
    public void onRefresh() {
        presenter.getNgoData(0, channel_list.get(0).getUrl());
    }

    @Override
    public void showChannelOne(BaseBean bean) {
        refreshLayout.setRefreshing(false);
        list.clear();

        list.add(new ListData());
        List<ListData> list_datas = ((ListDataBean) bean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(0).getCname());
        title.setUrl(channel_list.get(0).getUrl());
        title.setTitle("view");
        list.add(title);  //公示公告

        count_1 = list_datas.size();
        for (int i = 0; i < (list_datas.size() > 3 ? 3 : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }

        presenter.getNgoData(1, channel_list.get(1).getUrl());
    }

    @Override
    public void showChannelTwo(BaseBean bean) {
        List<ListData> list_datas = ((ListDataBean) bean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(1).getCname());
        title.setUrl(channel_list.get(1).getUrl());
        list.add(title); //NGO小组动态

        count_2 = list_datas.size();
        for (int i = 0; i < (list_datas.size() > 3 ? 3 : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }

        presenter.getNgoData(2, channel_list.get(2).getUrl());
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        List<ListData> data = ((ListDataBean) baseBean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(2).getCname());
        title.setUrl(channel_list.get(2).getUrl());
        list.add(title); //NGO小组

        ListData ngoData = new ListData();
        ngoData.setChannel_list(data);
        list.add(ngoData);

        list = DataHelper.initNgoList(count_1,count_2,list);

        if (adapter == null){
            adapter = new NgoAdapter(list, context);
            RecycleviewUtil.initGridRecycleView(recycleview, adapter, context, SPAN_COUNT);
        }else{
            adapter.updateData(list);
        }
    }

}
