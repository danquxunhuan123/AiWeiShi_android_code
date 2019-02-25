package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.FragmentAdapter;
import com.trs.aiweishi.base.BaseActivity;
import com.lf.http.bean.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.lf.http.bean.Img;
import com.lf.http.bean.ListData;
import com.lf.http.bean.ListDataBean;
import com.trs.aiweishi.listener.AppBarStateChangeListener;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.fragment.ZiXunFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ZiXunActivity extends BaseActivity {
    // implements OnChannelListener

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.view_padding)
    View viewPadding;
    @BindView(R.id.rl_zixun_top)
    RelativeLayout top;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.appbar_layout)
    AppBarLayout barLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    public final static String PARAM = "bean";
    public final static String PARAM2 = "toolbar_name";
    private ListData bean;
    private String toolbarName = "";

    @Override
    protected boolean isTranslucent() {
        return false;
    }

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
        barLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态

                } else if (state == State.COLLAPSED) {
                    //折叠状态
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.color_f6f6f6));

                } else {
                    //中间状态

                }
            }
        });
    }

    @Override
    protected void initData() {
        bean = getIntent().getParcelableExtra(PARAM);
        presenter.getChannelData(bean.getUrl());
    }

    @Override
    public int initLayout() {
        return R.layout.activity_zi_xun;
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        List<ListData> list_datas = ((ListDataBean) baseBean).getList_datas();
        int size = list_datas.size();
        if (list_datas != null && size > 0) {
            top.setVisibility(View.VISIBLE);

            if (size > 3)
                size = 3;
            for (int i = 0; i < size; i++) {
                View itemView = LayoutInflater.from(this).inflate(R.layout.list_item_commen_layout, null, false); //zixun_item_layout
                ImageView pic = itemView.findViewById(R.id.iv_pic);
                ImageView player = itemView.findViewById(R.id.iv_player);
                TextView name = itemView.findViewById(R.id.tv_name);
                TextView time = itemView.findViewById(R.id.tv_time);

                final ListData data = list_datas.get(i);
                setTopData(data, pic, player, name, time);

                llTop.addView(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDetail(data);
                    }
                });
            }
        }

        initViewPager();
    }

    private void setTopData(ListData data, ImageView pic, ImageView player, TextView tvName, TextView tvTime) {
        if (TextUtils.isEmpty(getImg(data.getImages()))) {
            pic.setVisibility(View.GONE);
        } else {
            pic.setVisibility(View.VISIBLE);
            GlideUtils.loadUrlImg(this, getImg(data.getImages()), pic);
        }
        tvName.setText(data.getTitle());

        if ("视频".equals(data.getArticleType())) {
            player.setVisibility(View.VISIBLE);
        } else {
            player.setVisibility(View.GONE);
        }

        if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
            tvTime.setVisibility(View.GONE);
        else
            tvTime.setText(data.getTime().split(" ")[0]);
    }

    private String getImg(List<Img> images) {
        return images.get(0).getSrc();
    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        List<BaseFragment> fragments = new ArrayList<>();
        List<ListData> channel_list = bean.getChannel_list();
        int size = channel_list.size();
        if (channel_list != null && size > 0) {
            for (int x = 0; x < size; x++) {
                ListData listData = channel_list.get(x);
                titles.add(listData.getCname());
                fragments.add(ZiXunFragment.newInstance(listData.getUrl(), toolbarName));
            }

            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(fragments.size());  //fragments.size()
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @OnClick(R.id.iv_back) //, R.id.ll_item, R.id.ll_item1, R.id.ll_item2, R.id.iv_channel_menu
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_channel_menu:
//                showMenu();
//                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void toDetail(ListData data) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.TITLE_NAME, toolbarName);
        intent.putExtra(DetailActivity.PARCELABLE, (Parcelable) data);
        intent.putExtra(DetailActivity.URL, data.getUrl());
        startActivity(intent);
    }

    //    private void showMenu() {
//        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(titles);
//        dialogFragment.setOnChannelListener(this);
//        dialogFragment.show(getSupportFragmentManager(), ChannelDialogFragment.TAG);
//        dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//            }
//        });
//    }

//    @Override
//    public void onItemClick(int position) {
//        viewPager.setCurrentItem(position);
//    }
//
//    @Override
//    public void onItemMove(int starPos, int endPos) {
//
//    }
//
//    @Override
//    public void onMoveToMyChannel(int starPos, int endPos) {
//
//    }
//
//    @Override
//    public void onMoveToOtherChannel(int starPos, int endPos) {
//
//    }
}
