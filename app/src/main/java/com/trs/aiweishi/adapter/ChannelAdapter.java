package com.trs.aiweishi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;

import java.util.List;

/**
 * Created by Liufan on 2018/7/9.
 */

public class ChannelAdapter extends BaseAdapter {
    public ChannelAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(parent.getContext(), R.layout.channel_layout,parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, final int position) {
        ((TextView)holder.getView(R.id.tv_channel)).setText((CharSequence) list.get(position));

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChannelClickListener.OnChanelClick(position);
            }
        });
    }

    private OnChannelClickListener onChannelClickListener;

    public interface OnChannelClickListener{
        void OnChanelClick(int position);
    }
    public void setOnChannelListener(OnChannelClickListener onChannelListener) {
        onChannelClickListener = onChannelListener;
    }
}
