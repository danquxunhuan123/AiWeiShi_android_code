package com.trs.aiweishi.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.trs.aiweishi.R;
import com.trs.aiweishi.view.ui.activity.UserConfigActivity;

import org.w3c.dom.Text;

/**
 * Created by Liufan on 2018/7/10.
 */

public class PopWindowUtil {
    private Context context;
    private PopupWindow window;
    private View contentView;

    public PopWindowUtil(Context context) {
        this.context = context;
    }

    public PopWindowUtil setContentView(int layoutId) {
        contentView = LayoutInflater.from(context).inflate(layoutId, null);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disMiss();
            }
        });
        return this;
    }

    public void disMiss() {
        if (window != null && window.isShowing())
            window.dismiss();
    }

    public void showAtLocation(View parent) {
        window = new PopupWindow(context);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(contentView);
        window.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public PopWindowUtil getView(int id, View.OnClickListener listener) {
        contentView.findViewById(id).setOnClickListener(listener);
        return this;
    }

    public PopWindowUtil setContent(int id, String text) {
        View view = contentView.findViewById(id);
        if (view instanceof TextView)
            ((TextView) view).setText(text);
        return this;
    }

    public View getView(int id) {
        return contentView.findViewById(id);
    }
}
