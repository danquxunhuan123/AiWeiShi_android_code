package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.ListData;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.activity.DetailActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/7/5.
 */

public class ListDataAdapter extends BaseAdapter {
    public ListDataAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_item_commen_layout, parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        final ListData bean = (ListData) list.get(position);

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
    }
}
