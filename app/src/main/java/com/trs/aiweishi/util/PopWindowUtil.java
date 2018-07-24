package com.trs.aiweishi.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.trs.aiweishi.R;
import com.trs.aiweishi.view.ui.activity.UserConfigActivity;

/**
 * Created by Liufan on 2018/7/10.
 */

public class PopWindowUtil {
    private static PopWindowUtil instance = null;
    private Context context;
    private PopupWindow window;
    private View contentView;

    public static PopWindowUtil getInstance(Context context){
        if (instance == null){
            instance = new PopWindowUtil(context);
        }

        return instance;
    }

    public PopWindowUtil(Context context) {
        this.context = context;
    }

    public PopWindowUtil setContentView(int layoutId){
        contentView = LayoutInflater.from(context).inflate(layoutId, null);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    window.dismiss();
                }
            }
        });
        window = new PopupWindow(context);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(contentView);
        return this;
    }

    public void disMiss(){
        if (window.isShowing())
            window.dismiss();
    }

    public void showAtLocation(View parent){
        window.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public PopWindowUtil getView(int id, View.OnClickListener listener){
        contentView.findViewById(id).setOnClickListener(listener);
        return this;
    }

    public View getView(int id){
        return contentView.findViewById(id);
    }
}
