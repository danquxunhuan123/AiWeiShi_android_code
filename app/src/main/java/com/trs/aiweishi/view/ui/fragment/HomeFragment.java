package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.HomeAdapter;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.ListDataBean;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.DataHelper;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.view.IHomeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Liufan on 2018/5/17.
 */
public class HomeFragment extends BaseFragment implements IHomeView,
        SwipeRefreshLayout.OnRefreshListener {
    @Inject
    IHomePresenter homePresenter;
    @BindView(R.id.view_padding)
    View viewPadding;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private final int listCount = 2;
    private final int SPAN_COUNT = 3;
    public static String mParam1 = "param1";
    private List<ListData> bannerDatas = new ArrayList<>();
    private List<ListData> list = new ArrayList<>();
    private List<ListData> channel_list;
    List<ListData> hudong_datas;

    private HomeAdapter adapter;

    public static BaseFragment newInstance(ListData channelBean) {
        BaseFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(mParam1, channelBean);
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

        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                //当前的recycleView不滑动(滑动已经停止时)
//                if (newState == RecyclerView.SCROLL_STATE_IDLE){
//                    BarUtils.setStatusBarVisibility(context,true);
//                }
                //当前的recycleView被拖动滑动
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING ){
//                    BarUtils.setStatusBarVisibility(context,false);
//                }
//                //当前的recycleView在滚动到某个位置的动画过程,但没有被触摸滚动
//                if (newState == RecyclerView.SCROLL_STATE_SETTLING ){
//                    BarUtils.setStatusBarVisibility(context,false);
//                }
//            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //false 为滑动到顶部
                if (!recyclerView.canScrollVertically(-1)){
//                    if (!BarUtils.isStatusBarVisible(context))
//                        BarUtils.setStatusBarVisibility(context,true);
                }
                else{
//                    if (BarUtils.isStatusBarVisible(context))
//                        BarUtils.setStatusBarVisibility(context,false);
                }
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
        ListData bean = null;
        if (getArguments() != null) {
            bean = getArguments().getParcelable(mParam1);
        }

        channel_list = bean.getChannel_list();

        homePresenter.getBannerData(channel_list.get(0).getUrl());

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//                onRefresh();
//            }
//        }, 500);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onStop() {
        super.onStop();
//        banner.stopAutoPlay();
    }

    @Override
    public void onStart() {
        super.onStart();
//        banner.startAutoPlay();
    }

    @Override
    public void onRefresh() {
        homePresenter.getBannerData(channel_list.get(0).getUrl());
    }

    @Override
    public void showBanner(BaseBean baseBean) {
        refreshLayout.setRefreshing(false);
        bannerDatas.clear();
        list.clear();

        list.add(new ListData());
        List<ListData> ld = ((ListDataBean) baseBean).getList_datas();
        if (ObjectUtils.isNotEmpty(ld)) {
            for (int a = 0; a < (ld.size() > 5 ? 5 : ld.size()); a++) {
                bannerDatas.add(ld.get(a));
            }
        }
        //轮播
        list.add(new ListData());
        //类别1
        ListData lb1 = new ListData();
        lb1.setCname(context.getResources().getString(R.string.zxun));
        list.add(lb1);
        //类别2
        ListData lb2 = new ListData();
        lb2.setCname(context.getResources().getString(R.string.jc));
        list.add(lb2);
        //类别接口
        List<ListData> lb_list = channel_list.get(3).getChannel_list();  //类别集合
        for (int a = 0; a < lb_list.size(); a++) {
            ListData l = new ListData();
            l.setCname(lb_list.get(a).getCname());
            l.setChannel_list(lb_list.get(a).getChannel_list());
            l.setUrl(lb_list.get(a).getUrl());
            list.add(l);
        }

        homePresenter.getHuoDongData(channel_list.get(1).getUrl());
    }

    @Override
    public void showHuoDong(BaseBean baseBean) {
        hudong_datas = ((ListDataBean) baseBean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(1).getCname());
        title.setUrl(channel_list.get(1).getUrl());
        list.add(title);  //最新活动

        for (int i = 0; i < (hudong_datas.size() > listCount ? listCount : hudong_datas.size()); i++) {
            list.add(hudong_datas.get(i));
        }

        homePresenter.getChannelData(channel_list.get(2).getUrl());
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        List<ListData> list_datas = ((ListDataBean) baseBean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(2).getCname());
        title.setUrl(channel_list.get(2).getUrl());
        list.add(title); //最新新闻

        for (int i = 0; i < (list_datas.size() > listCount ? listCount : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }

        List<ListData> listData = DataHelper.initHomeList(list, listCount);
        adapter = new HomeAdapter(listData, context, bannerDatas);
        RecycleviewUtil.initGridRecycleView(recycleview, adapter, context, SPAN_COUNT);
    }

}
