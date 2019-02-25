package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.DocAdapter;
import com.lf.http.bean.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.lf.http.bean.ListData;
import com.lf.http.bean.ListDataBean;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.DataHelper;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.lf.http.view.IHomeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Liufan on 2018/5/17.
 */
public class DocFragment extends BaseFragment implements IHomeView
        , SwipeRefreshLayout.OnRefreshListener {
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.view_padding)
    View viewPadding;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private final int listCount = 5;
    private int list1 = 0;
    public static String mParam1;
    private List<ListData> bannerDatas = new ArrayList<>();
    private List<ListData> list = new ArrayList<>();
    private final int SPAN_COUNT = 3;
    private List<ListData> channel_list;

    public DocFragment() {

    }

    public static DocFragment newInstance(ListData channelBean) {
        DocFragment fragment = new DocFragment();
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
        viewPadding.setBackgroundColor(getResources().getColor(R.color.color_2f79e9));

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
        presenter.getBannerData(channel_list.get(0).getUrl());
        MProgressDialog.showProgress(context, config);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//                onRefresh();
//            }
//
//        }, 500);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_consult;
    }

    @Override
    public void onRefresh() {
        presenter.getBannerData(channel_list.get(0).getUrl());
    }

    @Override
    public void showBanner(BaseBean baseBean) {
        refreshLayout.setRefreshing(false);
        bannerDatas.clear();
        list.clear();

        List<ListData> ld = ((ListDataBean) baseBean).getList_datas();
        if (ObjectUtils.isNotEmpty(ld)) {
            for (int a = 0; a < (ld.size() > listCount ? listCount : ld.size()); a++) {
                bannerDatas.add(ld.get(a));
            }
        }

        list.add(new ListData());
        List<ListData> lb_list = channel_list.get(3).getChannel_list();  //类别集合
        for (int a = 0; a < lb_list.size(); a++) {
            ListData l = new ListData();
            l.setCname(lb_list.get(a).getCname());
            l.setChannel_list(lb_list.get(a).getChannel_list());
            l.setUrl(lb_list.get(a).getUrl());
            l.setChannelType(1);
            list.add(l);
        }

        presenter.getHuoDongData(channel_list.get(1).getUrl());
    }

    @Override
    public void showHuoDong(BaseBean baseBean) {
        List<ListData> list_datas = ((ListDataBean) baseBean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(1).getCname());
        title.setUrl(channel_list.get(1).getUrl());
        list.add(title);  //最新活动

        list1 = list_datas.size() > listCount ? listCount : list_datas.size();
        for (int i = 0; i < (list_datas.size() > listCount ? listCount : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }

        presenter.getChannelData(channel_list.get(2).getUrl());
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        MProgressDialog.dismissProgress();

        List<ListData> list_datas = ((ListDataBean) baseBean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(2).getCname());
        title.setUrl(channel_list.get(2).getUrl());
        list.add(title); //最新新闻

        for (int i = 0; i < (list_datas.size() > listCount ? listCount : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }

        list = DataHelper.initDocList(list, list1);

        DocAdapter adapter = new DocAdapter(list, context, bannerDatas);
        RecycleviewUtil.initGridRecycleView(recycleview, adapter, context, SPAN_COUNT);
    }

}
