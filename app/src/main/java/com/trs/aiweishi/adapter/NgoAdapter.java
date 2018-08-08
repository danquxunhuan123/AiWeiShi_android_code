package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
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
import com.trs.aiweishi.view.ui.activity.ZiXunActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liufan on 2018/5/17.
 */

public class NgoAdapter extends BaseAdapter {

    public NgoAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ListData.PAGE_HEAD_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.nog_head_layout, parent);
            case ListData.ITEM_TITLE_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.list_item_title_layout, parent);
            case ListData.SCROOL_LINEAR_TYPE:
                return MyHolder.getComViewHolder(context, R.layout.horizontal_scrollview_linear_layout, parent);
            default:
                return MyHolder.getComViewHolder(context, R.layout.list_item_commen_layout, parent);
        }
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        final ListData bean = (ListData) list.get(position);
        switch (getItemViewType(position)) {
            case ListData.PAGE_HEAD_TYPE:
                break;
            case ListData.ITEM_TITLE_TYPE:
                if (TextUtils.isEmpty(bean.getTitle())){
                    (holder.getView(R.id.view_title_top)).setVisibility(View.VISIBLE);
                }else{
                    (holder.getView(R.id.view_title_top)).setVisibility(View.GONE);
                }

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
            case ListData.SCROOL_LINEAR_TYPE:
                LinearLayout line = (LinearLayout) holder.getView(R.id.ll_content);
                line.removeAllViews();
                for (int a = 0;a < bean.getChannel_list().size(); a ++){
                    ListData data = bean.getChannel_list().get(a);
                    View view = LayoutInflater.from(context).inflate(R.layout.scrool_linear_item, line, false);
                    ImageView iv = view.findViewById(R.id.iv_scrool);
                    TextView tv = view.findViewById(R.id.tv_scrool);
                    GlideUtils.loadUrlImg(context,data.getImages().get(0).getSrc(), iv);
                    tv.setText(data.getTitle());

                    final ListData finalData = data;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra(DetailActivity.TITLE_NAME, finalData.getCname());
                            intent.putExtra(DetailActivity.URL, finalData.getUrl());
                            context.startActivity(intent);
                        }
                    });
                    line.addView(view);
                }
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
}
