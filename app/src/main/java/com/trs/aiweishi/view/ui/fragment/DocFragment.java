package com.trs.aiweishi.view.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.DocAdapter;
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
public class DocFragment extends BaseFragment implements IHomeView
        , SwipeRefreshLayout.OnRefreshListener {
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    public static String mParam1;
    private List<ListData> bannerDatas = new ArrayList<>();
    private List<ListData> list = new ArrayList<>();
    private DocAdapter adapter;  //
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
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
        ListData bean = null;
        if (getArguments() != null) {
            bean = getArguments().getParcelable(mParam1);
        }
        channel_list = bean.getChannel_list();
        presenter.getBannerData(channel_list.get(0).getUrl());
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
            for (int a = 0; a < (ld.size() > 5 ? 5 : ld.size()); a ++){
                bannerDatas.add(ld.get(a));
            }
        }

        list.add(new ListData());
        List<ListData> lb_list = channel_list.get(3).getChannel_list();  //类别集合
        for (int a =0 ; a < lb_list.size(); a ++){
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

        for (int i = 0; i < (list_datas.size() > 2 ? 2 : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }

        presenter.getChannelData(channel_list.get(2).getUrl());
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        List<ListData> list_datas = ((ListDataBean) baseBean).getList_datas();
        ListData title = new ListData();
        title.setCname(channel_list.get(2).getCname());
        title.setUrl(channel_list.get(2).getUrl());
        list.add(title); //最新新闻

        for (int i = 0; i < (list_datas.size() > 2 ? 2 : list_datas.size()); i++) {
            list.add(list_datas.get(i));
        }


        list = DataHelper.initDocList(context,list);

        adapter = new DocAdapter(list, context,bannerDatas);
        RecycleviewUtil.initGridRecycleView(recycleview, adapter, context, SPAN_COUNT);
    }

}
