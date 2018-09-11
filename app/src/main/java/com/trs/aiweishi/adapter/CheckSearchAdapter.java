package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.view.ui.activity.CheckDetailActivity;

import java.util.List;

/**
 * Created by Liufan on 2018/7/6.
 */

public class CheckSearchAdapter extends BaseAdapter {
    public CheckSearchAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.check_item_layout, parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        final SearchBean.SearchData bean = (SearchBean.SearchData) list.get(position);

        ((TextView) holder.getView(R.id.tv_name)).setText(Html.fromHtml(bean.getORGNAME()));
        ((TextView) holder.getView(R.id.tv_address)).setText(Html.fromHtml(bean.getORGADDR()));
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckDetailActivity.class);
                intent.putExtra(CheckDetailActivity.TAG, bean);
                intent.putExtra(CheckDetailActivity.INTEXTRA, 1);
                context.startActivity(intent);
            }
        });
    }
}
