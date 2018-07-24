package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.activity.CheckDetailActivity;
import com.trs.aiweishi.view.ui.activity.DetailActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/7/6.
 */

public class SearchAdapter extends BaseAdapter {
    public SearchAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.list_item_commen_layout, parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        final SearchBean.SearchData bean = (SearchBean.SearchData) list.get(position);

//        ImageView imageView = (ImageView) holder.getView(R.id.iv_pic);
//        if (ObjectUtils.isNotEmpty(bean.getImages())){
//            imageView.setVisibility(View.VISIBLE);
//            GlideUtils.loadUrlImg(context
//                    ,bean.getImages().get(0).getSrc(),imageView);
//        }else{
//            imageView.setVisibility(View.GONE);
//        }

        ((TextView) holder.getView(R.id.tv_name)).setText(Html.fromHtml(bean.getDoctitle()));
        ((TextView) holder.getView(R.id.tv_time)).setText(bean.getDocreltime());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.URL, bean.getDocpuburl());
                context.startActivity(intent);
            }
        });

    }
}
