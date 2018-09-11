package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.Site;
import com.trs.aiweishi.view.ui.activity.CheckDetailActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liufan on 2018/6/28.
 */

public class CheckAdapter extends BaseAdapter {
    public CheckAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(context, R.layout.check_item_layout1, parent);
    }


    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        final Site.Monitor bean = (Site.Monitor) list.get(position);
        ((TextView) holder.getView(R.id.tv_name)).setText(bean.getOrgName());
        holder.getView(R.id.tv_yuyue).setBackground(null); // 可预约和暂停预约
//        if ("0".equals(bean.getIsFree()))
            holder.getView(R.id.tv_free).setVisibility(View.GONE);
//        else
//            holder.getView(R.id.tv_free).setVisibility(View.VISIBLE);

        ((TextView) holder.getView(R.id.tv_address)).setText(bean.getOrgAddr());

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckDetailActivity.class);
                intent.putExtra(CheckDetailActivity.TAG, bean);
                context.startActivity(intent);
            }
        });
    }

}
