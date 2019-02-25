package com.trs.aiweishi.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.Site;

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

        if ("1".equals(bean.getReservable())) //可预约
            holder.getView(R.id.tv_yuyue).setBackground(context.getResources().getDrawable(R.mipmap.icon_check_kyy));
        else {
            holder.getView(R.id.tv_yuyue).setBackground(context.getResources().getDrawable(R.mipmap.icon_check_ztyy)); // 暂停预约
        }

        if ("1".equals(bean.getIsFree()))
            holder.getView(R.id.tv_free).setVisibility(View.VISIBLE);
        else
            holder.getView(R.id.tv_free).setVisibility(View.GONE);

        ((TextView) holder.getView(R.id.tv_address)).setText(bean.getOrgAddr());

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCheckClick((Parcelable) bean);
            }
        });
    }

    private OnCheckClickListener listener;

    public void setOnCheckClickListener(OnCheckClickListener listener) {
        this.listener = listener;
    }

    public interface OnCheckClickListener {
        void onCheckClick(Parcelable parcelable);
    }

}
