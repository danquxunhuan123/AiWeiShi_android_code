package com.trs.aiweishi.view.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.FragmentAdapter;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BaseFragment;
import com.trs.aiweishi.bean.Img;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.ListDataBean;
import com.trs.aiweishi.listener.AppBarStateChangeListener;
import com.trs.aiweishi.listener.OnChannelListener;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.fragment.ChannelDialogFragment;
import com.trs.aiweishi.view.ui.fragment.ZiXunFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ZiXunActivity extends BaseActivity implements OnChannelListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.view_padding)
    View viewPadding;
    @BindView(R.id.ll_zixun_top)
    LinearLayout top;
    @BindView(R.id.ll_item)
    LinearLayout item;
    @BindView(R.id.ll_item1)
    LinearLayout item1;
    @BindView(R.id.ll_item2)
    LinearLayout item2;
    @BindView(R.id.iv_channel_menu)
    ImageView channelMenu;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.iv_pic)
    ImageView ivPicOne;
    @BindView(R.id.tv_name)
    TextView tvNameOne;
    @BindView(R.id.tv_time)
    TextView tvTimeOne;
    @BindView(R.id.iv_pic1)
    ImageView ivPicTwo;
    @BindView(R.id.tv_name1)
    TextView tvNameTwo;
    @BindView(R.id.tv_time1)
    TextView tvTimeTwo;
    @BindView(R.id.iv_pic2)
    ImageView ivPicThree;
    @BindView(R.id.tv_name2)
    TextView tvNameThree;
    @BindView(R.id.tv_time2)
    TextView tvTimeThree;
    @BindView(R.id.appbar_layout)
    AppBarLayout barLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    public final static String PARAM = "bean";
    public final static String PARAM2 = "toolbar_name";
    private List<String> titles;
    private ListData bean;
    private ListData data;
    private ListData data1;
    private ListData data2;
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

        if (list_datas != null && list_datas.size() > 0) {
            if (list_datas.size() == 1) {
                item.setVisibility(View.VISIBLE);
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);

                data = list_datas.get(0);
                if (TextUtils.isEmpty(getImg(data.getImages()))) {
                    ivPicOne.setVisibility(View.GONE);
                } else {
                    ivPicOne.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(this, getImg(data.getImages()), ivPicOne);
                }
                tvNameOne.setText(data.getTitle());

                if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
                    tvTimeOne.setVisibility(View.GONE);
                else
                    tvTimeOne.setText(data.getTime().split(" ")[0]);
            } else if (list_datas.size() == 2) {
                item.setVisibility(View.VISIBLE);
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.GONE);

                //one
                data = list_datas.get(0);
                if (TextUtils.isEmpty(getImg(data.getImages()))) {
                    ivPicOne.setVisibility(View.GONE);
                } else {
                    ivPicOne.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(this, getImg(data.getImages()), ivPicOne);
                }
                tvNameOne.setText(data.getTitle());

                if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
                    tvTimeOne.setVisibility(View.GONE);
                else
                    tvTimeOne.setText(data.getTime().split(" ")[0]);
                //two
                data1 = list_datas.get(1);
                if (TextUtils.isEmpty(getImg(data1.getImages()))) {
                    ivPicTwo.setVisibility(View.GONE);
                } else {
                    ivPicTwo.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(this, getImg(data1.getImages()), ivPicTwo);
                }
                tvNameTwo.setText(data1.getTitle());

                if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
                    tvTimeTwo.setVisibility(View.GONE);
                else
                    tvTimeTwo.setText(data1.getTime().split(" ")[0]);
            } else {
                item.setVisibility(View.VISIBLE);
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.VISIBLE);

                //one
                data = list_datas.get(0);
                if (TextUtils.isEmpty(getImg(data.getImages()))) {
                    ivPicOne.setVisibility(View.GONE);
                } else {
                    ivPicOne.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(this, getImg(data.getImages()), ivPicOne);
                }
                tvNameOne.setText(data.getTitle());

                if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
                    tvTimeOne.setVisibility(View.GONE);
                else
                    tvTimeOne.setText(data.getTime().split(" ")[0]);
                //two
                data1 = list_datas.get(1);
                if (TextUtils.isEmpty(getImg(data1.getImages()))) {
                    ivPicTwo.setVisibility(View.GONE);
                } else {
                    ivPicTwo.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(this, getImg(data1.getImages()), ivPicTwo);
                }
                tvNameTwo.setText(data1.getTitle());

                if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
                    tvTimeTwo.setVisibility(View.GONE);
                else
                    tvTimeTwo.setText(data1.getTime().split(" ")[0]);
                //three
                data2 = list_datas.get(2);
                if (TextUtils.isEmpty(getImg(data2.getImages()))) {
                    ivPicThree.setVisibility(View.GONE);
                } else {
                    ivPicThree.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(this, getImg(data2.getImages()), ivPicThree);
                }
                tvNameThree.setText(data2.getTitle());

                if ("知识".equals(toolbarName) || "校园".equals(toolbarName))
                    tvTimeThree.setVisibility(View.GONE);
                else
                    tvTimeThree.setText(data2.getTime().split(" ")[0]);
            }
        } else {
            top.setVisibility(View.GONE);
        }

        initViewPager();
    }

    private String getImg(List<Img> images) {
        return images.get(0).getSrc();
    }

    private void initViewPager() {
        titles = new ArrayList<>();
        List<BaseFragment> fragments = new ArrayList<>();
        List<ListData> channel_list = bean.getChannel_list();
        if (channel_list != null && channel_list.size() > 0) {
            for (int x = 0; x < channel_list.size(); x++) {
                titles.add(channel_list.get(x).getCname());
                fragments.add(ZiXunFragment.newInstance(channel_list.get(x).getUrl(), toolbarName));
            }

            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(fragments.size());  //fragments.size()
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_item, R.id.ll_item1, R.id.ll_item2, R.id.iv_channel_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_channel_menu:
                showMenu();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_item:
                toDetail(data.getUrl());
                break;
            case R.id.ll_item1:
                toDetail(data1.getUrl());
                break;
            case R.id.ll_item2:
                toDetail(data2.getUrl());
                break;
        }
    }

    private void showMenu() {
        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(titles);
        dialogFragment.setOnChannelListener(this);
        dialogFragment.show(getSupportFragmentManager(), ChannelDialogFragment.TAG);
        dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
    }

    private void toDetail(String url) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.TITLE_NAME, toolbarName);
        intent.putExtra(DetailActivity.URL, url);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {

    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {

    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {

    }
}
