package com.trs.aiweishi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;
import com.lf.http.bean.CheckPackegBean;
import com.lf.http.bean.CheckResult;
import com.trs.aiweishi.view.ui.activity.MyCheckHistoryActivity;
import com.trs.aiweishi.view.ui.activity.UploadPicActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/12 0012.
 */

public class ChecksInfoAdapter extends BaseAdapter {
    private int type = 0;

    public ChecksInfoAdapter(List listData, Context context) {
        super(listData, context);
    }

    public void setTypeCheckHistory(int type) {
        this.type = type;
    }

    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        if (type == 0) {
            return MyHolder.getComViewHolder(context, R.layout.check_info_item_layout, parent);
        } else if (type == MyCheckHistoryActivity.TYPE) {
            return MyHolder.getComViewHolder(context, R.layout.check_result_layout, parent);
        } else
            return null;
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        if (type == 0) {
            CheckResult.CResult result = (CheckResult.CResult) list.get(position);
            ((TextView) holder.getView(R.id.tv_check_time)).setText(
                    String.format(context.getResources().getString(R.string.check_info_time)
                            , result.getCheckTime()));
            ((TextView) holder.getView(R.id.tv_check_address)).setText(
                    String.format(context.getResources().getString(R.string.check_info_address)
                            , result.getCheckPlace()));
            ((TextView) holder.getView(R.id.tv_check_result)).setText(
                    String.format(context.getResources().getString(R.string.check_info_result)
                            , result.getCheckResult()));
            ((TextView) holder.getView(R.id.tv_check_way)).setText(result.getCheckWay());
        } else {
            final CheckPackegBean.CPackeg result = (CheckPackegBean.CPackeg) list.get(position);
            ((TextView) holder.getView(R.id.tv_yuyue_time)).setText(
                    String.format(context.getResources().getString(R.string.check_info_time)
                            , result.getBookingTime()));
            ((TextView) holder.getView(R.id.tv_yuyue_name)).setText(
                    String.format(context.getResources().getString(R.string.check_info_address)
                            , result.getMonitoringPoint()));

            if ("已寄出".equals(result.getPackageStatus())) {
                holder.getView(R.id.ll_upload_img).setVisibility(View.VISIBLE);
                holder.getView(R.id.but_upload_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, UploadPicActivity.class);
                        intent.putExtra(UploadPicActivity.PACKAGE, result);
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.getView(R.id.ll_upload_img).setVisibility(View.GONE);
            }

        }
    }
}
