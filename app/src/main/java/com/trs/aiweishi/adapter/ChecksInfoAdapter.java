package com.trs.aiweishi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.CheckResult;

import java.util.List;

/**
 * Created by Administrator on 2018/11/12 0012.
 */

public class ChecksInfoAdapter extends BaseAdapter {
    public ChecksInfoAdapter(List listData, Context context) {
        super(listData, context);
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(context, R.layout.check_info_item_layout, parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {

        CheckResult.CResult result = (CheckResult.CResult) list.get(position);
        ((TextView)holder.getView(R.id.tv_check_time)).setText(
                String.format(context.getResources().getString(R.string.check_info_time)
                        ,result.getCheckTime()));
        ((TextView)holder.getView(R.id.tv_check_address)).setText(
                String.format(context.getResources().getString(R.string.check_info_address)
                        ,result.getCheckPlace()));
        ((TextView)holder.getView(R.id.tv_check_result)).setText(
                String.format(context.getResources().getString(R.string.check_info_result)
                        ,result.getCheckResult()));
    }
}
