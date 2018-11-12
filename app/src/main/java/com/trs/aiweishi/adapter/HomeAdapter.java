package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.TextDrawableBean;
import com.trs.aiweishi.util.BannerHelper;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.custview.MyBanner;
import com.trs.aiweishi.view.ui.activity.CheckActivity;
import com.trs.aiweishi.view.ui.activity.DetailActivity;
import com.trs.aiweishi.view.ui.activity.ListDataActivity;
import com.trs.aiweishi.view.ui.activity.SearchActivity;
import com.trs.aiweishi.view.ui.activity.ZiXunActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/5/17.
 */

public class HomeAdapter extends BaseAdapter implements MyBanner.OnItemClickListener {

    private List<ListData> bannerData;

    public HomeAdapter(List list, Context context, List<ListData> bannerData) {
        super(list, context);

        this.bannerData = bannerData;
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ListData.PAGE_HEAD_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.home_head_layout, parent);
            case ListData.BANNER_HEAD_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.list_header_layout, parent);
            case TextDrawableBean.TEXT_DRAWABLE_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.text_left_img_layout, parent);
            case ListData.ITEM_TITLE_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.list_item_title_layout, parent);
            default:
                return MyHolder.getComViewHolder(context, R.layout.list_item_commen_layout, parent);
        }
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        final ListData bean = (ListData) list.get(position);
        switch (getItemViewType(position)) {
            case ListData.PAGE_HEAD_TYPE:
                View itemView = holder.getItemView();
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, SearchActivity.class));
                    }
                });
                break;
            case ListData.BANNER_HEAD_TYPE:
                MyBanner banner = (MyBanner) holder.getView(R.id.mbanner);
                BannerHelper.setConvenientBanner(banner, bannerData, this);
                break;
            case TextDrawableBean.TEXT_DRAWABLE_TYPE:
                TextView tv = (TextView) holder.getView(R.id.tv);
                View lineBottom = holder.getView(R.id.line_bottom);
                View lineRighBott = holder.getView(R.id.line_right_bottom);
                View lineRighTop = holder.getView(R.id.line_right_top);

                final String name = bean.getCname();
                tv.setText(name);
                int draId = bean.getDrawable();
                tv.setCompoundDrawablesWithIntrinsicBounds(
                        context.getResources().getDrawable(draId),
                        null, null, null);

                if (draId == R.mipmap.icon_zx) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lineBottom.getLayoutParams();
                    params.setMargins(SizeUtils.dp2px(10), 0, 0, 0);
                    lineBottom.setLayoutParams(params);

                    lineBottom.setVisibility(View.VISIBLE);
                    lineRighBott.setVisibility(View.VISIBLE);
                    lineRighTop.setVisibility(View.GONE);
                } else if (draId == R.mipmap.icon_jishu) {
                    lineBottom.setVisibility(View.VISIBLE);
                    lineRighBott.setVisibility(View.VISIBLE);
                    lineRighTop.setVisibility(View.GONE);
                } else if (draId == R.mipmap.icon_yiyao) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lineBottom.getLayoutParams();
                    params.setMargins(0, 0, SizeUtils.dp2px(10), 0);
                    lineBottom.setLayoutParams(params);

                    lineBottom.setVisibility(View.VISIBLE);
                    lineRighBott.setVisibility(View.GONE);
                    lineRighTop.setVisibility(View.GONE);
                } else if (draId == R.mipmap.icon_zhishi || draId == R.mipmap.icon_zixun) {
                    lineBottom.setVisibility(View.GONE);
                    lineRighBott.setVisibility(View.GONE);
                    lineRighTop.setVisibility(View.VISIBLE);
                } else {
                    lineBottom.setVisibility(View.GONE);
                    lineRighBott.setVisibility(View.GONE);
                    lineRighTop.setVisibility(View.GONE);
                }

                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.getChannelType() == 1) {
                            Intent intent = new Intent(context, ListDataActivity.class);
                            intent.putExtra(ListDataActivity.PARAM1, bean.getUrl());
                            intent.putExtra(ListDataActivity.PARAM2, bean.getCname());
                            context.startActivity(intent);
                        } else {
                            if (context.getResources().getString(R.string.zxun).equals(bean.getCname())) {
                                Intent intent = new Intent(context, DetailActivity.class);
                                intent.putExtra(DetailActivity.URL, AppConstant.XIAOSI);
                                intent.putExtra(DetailActivity.TITLE_NAME, "智慧咨询");
                                intent.putExtra(DetailActivity.TYPE, 3);
                                context.startActivity(intent);
                            } else if (context.getResources().getString(R.string.jc).equals(bean.getCname())) {
                                context.startActivity(new Intent(context, CheckActivity.class));
                            } else {
                                Intent intent = new Intent(context, ZiXunActivity.class);
                                intent.putExtra(ZiXunActivity.PARAM, (Parcelable) bean);
                                intent.putExtra(ZiXunActivity.PARAM2, bean.getCname());
                                context.startActivity(intent);
                            }
                        }
                    }
                });
                break;
            case ListData.ITEM_TITLE_TYPE:
                ((TextView) holder.getView(R.id.tv_title)).setText(bean.getCname());

                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ListDataActivity.class);
                        intent.putExtra(ListDataActivity.PARAM1, bean.getUrl());
                        intent.putExtra(ListDataActivity.PARAM2, bean.getCname());
                        context.startActivity(intent);
                    }
                });
                break;
            case ListData.ITEM_COMMEN_TYPE:
                ImageView imageView = (ImageView) holder.getView(R.id.iv_pic);
                if (ObjectUtils.isNotEmpty(bean.getImages())) {
                    imageView.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(context
                            , bean.getImages().get(0).getSrc(), imageView);
                } else {
                    imageView.setVisibility(View.GONE);
                }

                ((TextView) holder.getView(R.id.tv_name)).setText(bean.getTitle());
                if (!TextUtils.isEmpty(bean.getTime()))
                    ((TextView) holder.getView(R.id.tv_time)).setText(bean.getTime().split(" ")[0]);
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(DetailActivity.TITLE_NAME, bean.getCname());
                        intent.putExtra(DetailActivity.URL, bean.getUrl());
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra(DetailActivity.TITLE_NAME, bannerData.get(position).getCname());
        intent.putExtra(DetailActivity.URL, bannerData.get(position).getUrl());
        context.startActivity(intent);
    }
}
