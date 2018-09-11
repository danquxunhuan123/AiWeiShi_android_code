package com.trs.aiweishi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseAdapter;

import java.util.List;

/**
 * Created by Liufan on 2018/9/6.
 */

public class WorkTimeAdapter extends BaseAdapter {
    private TextView currentTextView;

    public WorkTimeAdapter(List listData, Context context) {
        super(listData, context);
    }


    @Override
    public MyHolder getViewHolder(ViewGroup parent, int viewType) {
        return MyHolder.getComViewHolder(context, R.layout.list_worktime_layout, parent);
    }

    @Override
    protected void bindMyViewHolder(MyHolder holder, int position) {
        String time = (String) list.get(position);
        final TextView textTime = (TextView) holder.getView(R.id.tv_worktime);

        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) textTime.getLayoutParams();
        param.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(10) * 4) / 3;
        textTime.setLayoutParams(param);
        textTime.setText(time);

        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSelectListener.OnTimeSelect(textTime.getText());
                if (currentTextView != null){
                    currentTextView.setTextColor(context.getResources().getColor(R.color.color_3970e7));
                    currentTextView.setBackground(context.getResources().getDrawable(R.drawable.border_blue_trans));
                }
                textTime.setTextColor(Color.WHITE);
                textTime.setBackground(context.getResources().getDrawable(R.drawable.border_blue_no_trans));
                currentTextView = textTime;
            }
        });
    }

    private  OnTimeSelectListener onTimeSelectListener;
    public void setOnTimeSelectListener(OnTimeSelectListener onTimeSelectListener){
        this.onTimeSelectListener = onTimeSelectListener;
    }

    public interface OnTimeSelectListener{
        void OnTimeSelect(CharSequence text);
    }
}
