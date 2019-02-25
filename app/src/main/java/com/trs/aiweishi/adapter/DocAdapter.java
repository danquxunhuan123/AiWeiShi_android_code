package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.ListData;
import com.trs.aiweishi.bean.TextDrawableBean;
import com.trs.aiweishi.util.BannerHelper;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.custview.MyBanner_1;
import com.trs.aiweishi.view.ui.activity.DetailActivity;
import com.trs.aiweishi.view.ui.activity.ListDataActivity;
import com.trs.aiweishi.view.ui.activity.ZiXunActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/5/17.
 */

public class DocAdapter extends BaseAdapter implements MyBanner_1.OnItemClickListener {

    private List<ListData> bannerData;

    public DocAdapter(List list, Context context, List<ListData> bannerData) {
        super(list, context);

        this.bannerData = bannerData;
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ListData.BANNER_HEAD_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.list_header_layout_1, parent);
            case TextDrawableBean.TEXT_DRAWABLE_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.text_img_layout, parent);
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
            case ListData.BANNER_HEAD_TYPE:
                MyBanner_1 banner = (MyBanner_1) holder.getView(R.id.mbanner);
                BannerHelper.setConvenientBanner1(banner, bannerData, this);
                break;
            case TextDrawableBean.TEXT_DRAWABLE_TYPE:
                TextView tv = (TextView) holder.getView(R.id.tv);
                final String name = bean.getCname();
                tv.setText(name);
                tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        context.getResources().getDrawable(bean.getDrawable()),
                        null, null);

                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.getChannelType() == 1) {
                            Intent intent = new Intent(context, ListDataActivity.class);
                            intent.putExtra(ListDataActivity.PARAM1, bean.getUrl());
                            intent.putExtra(ListDataActivity.PARAM2, bean.getCname());
                            context.startActivity(intent);
                        } else {
//                            if (context.getResources().getString(R.string.zxun).equals(bean.getCname())) {
//                                Intent intent = new Intent(context, DetailActivity.class);
//                                intent.putExtra(DetailActivity.URL, AppConstant.XIAOSI);
//                                intent.putExtra(DetailActivity.TYPE, 3);
//                                context.startActivity(intent);
//                            } else if (context.getResources().getString(R.string.jc).equals(bean.getCname())) {
//                                context.startActivity(new Intent(context, CheckActivity.class));
//                            } else {
                            Intent intent = new Intent(context, ZiXunActivity.class);
                            intent.putExtra(ZiXunActivity.PARAM, (Parcelable) bean);
                            intent.putExtra(ZiXunActivity.PARAM2, bean.getCname());
                            context.startActivity(intent);
//                            }
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

                ImageView player = (ImageView) holder.getView(R.id.iv_player);
                if ("视频".equals(bean.getArticleType())) {
                    player.setVisibility(View.VISIBLE);
                } else {
                    player.setVisibility(View.GONE);
                }

                ((TextView) holder.getView(R.id.tv_name)).setText(bean.getTitle());
                if (!StringUtils.isEmpty(bean.getTime()))
                    ((TextView) holder.getView(R.id.tv_time)).setText(bean.getTime().split(" ")[0]);
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(DetailActivity.TITLE_NAME, bean.getCname());
                        intent.putExtra(DetailActivity.PARCELABLE, (Parcelable) bean);
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
        intent.putExtra(DetailActivity.URL, bannerData.get(position).getUrl());
        context.startActivity(intent);
    }
}
