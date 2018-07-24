package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.activity.DetailActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/6/28.
 */

public class ZiXunAdapter extends BaseAdapter {
    public ZiXunAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(context, R.layout.zixun_item_layout, parent);
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

        ((TextView) holder.getView(R.id.tv_name)).setText(bean.getCname());
        ((TextView) holder.getView(R.id.tv_time)).setText(bean.getTime());

//        ((ImageView)holder.getView(R.id.iv_pic)).setImageResource(R.mipmap.icon_pic);

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.URL, bean.getUrl());
                context.startActivity(intent);
            }
        });
    }

}
