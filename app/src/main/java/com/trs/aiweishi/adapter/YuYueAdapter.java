package com.trs.aiweishi.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.YuYueItem;

import java.util.List;

/**
 * Created by Liufan on 2018/9/11.
 */

public class YuYueAdapter extends BaseAdapter {
    public YuYueAdapter(List listData, Context context) {
        super(listData, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.yuyue_item_layout, parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {

        YuYueItem item = (YuYueItem) list.get(position);
        ((TextView)holder.getView(R.id.tv_yuyue_name)).setText(item.getMonitoringPoint());
        ((TextView)holder.getView(R.id.tv_yuyue_time)).setText(item.getBookingTime());
    }
}
