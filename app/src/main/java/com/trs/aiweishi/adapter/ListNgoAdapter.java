package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.ListData;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.activity.DetailActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/7/5.
 */

public class ListNgoAdapter extends BaseAdapter {
    public ListNgoAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_item_ngo_layout, parent);
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

        holder.getView(R.id.tv_yuyue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("敬请期待~");
//                Intent intent = new Intent(context, CheckDetailActivity.class);
//                intent.putExtra(CheckDetailActivity.TAG, bean);
//                context.startActivity(intent);
            }
        });
        holder.getView(R.id.tv_shenqing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("敬请期待~");
            }
        });
        ((TextView) holder.getView(R.id.tv_name)).setText(bean.getTitle());
        ((TextView) holder.getView(R.id.tv_content)).setText(bean.getAbs());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.TITLE_NAME, bean.getCname());
                intent.putExtra(DetailActivity.URL, bean.getUrl());
                context.startActivity(intent);
            }
        });
    }
}
