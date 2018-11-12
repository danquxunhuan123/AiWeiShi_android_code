package com.trs.aiweishi.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trs.aiweishi.R;

/**
 * Created by Liufan on 2018/7/10.
 */

public class AlertDialogUtil {
    private AlertDialog.Builder builder;
    private Context context;
    private View view;
    private Dialog dialog;
    private OnClickListener clickListener;

    public AlertDialogUtil(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    /**
     * 自定义dialog布局
     *
     * @param layoutId
     * @return
     */
    public AlertDialogUtil setContentView(int layoutId) {
        view = LayoutInflater.from(context).inflate(layoutId, null);
        builder.setView(view);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        return this;
    }

    /**
     * 默认dialog布局
     *
     * @return
     */
    public AlertDialogUtil setDialogView() {
        view = LayoutInflater.from(context).inflate(R.layout.update_dialog_layout, null);
        view.findViewById(R.id.dialog_sure).setOnClickListener(listener);
        view.findViewById(R.id.dialog_cancel).setOnClickListener(listener);
        builder.setView(view);
        return this;
    }

    public void create() {
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
//        dialog.getWindow().setDialogView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 给默认布局设置内容
     *
     * @param content
     * @return
     */
    public AlertDialogUtil setDialogContent(String content) {
        TextView contentView = view.findViewById(R.id.tv_dialog_content);
        contentView.setVisibility(View.VISIBLE);
        contentView.setText(content);
        return this;
    }

    /**
     * 给默认布局设置标题
     */
    public AlertDialogUtil setDialogTitle(String content) {
        TextView title = view.findViewById(R.id.tv_dialog_title);
        title.setText(content);
        return this;
    }

    /**
     * 给默认布局设置标题
     */
    public AlertDialogUtil setCancleColor(int colorValue) {
        TextView cancel = view.findViewById(R.id.dialog_cancel);
        cancel.setTextColor(colorValue);
        return this;
    }

    public AlertDialogUtil setSureColor(int colorValue) {
        TextView sure = view.findViewById(R.id.dialog_sure);
        sure.setTextColor(colorValue);
        return this;
    }

    public AlertDialogUtil setViewText(int id, String text) {
        View contentView = view.findViewById(id);
        if (contentView instanceof TextView)
            ((TextView) contentView).setText(text);
        if (contentView instanceof EditText)
            ((EditText) contentView).setText(text);
        return this;
    }

    public View getViewText(int id) {
        return view.findViewById(id);
    }

    public AlertDialogUtil setViewClickListener(int id, View.OnClickListener listener) {
        view.findViewById(id).setOnClickListener(listener);
        return this;
    }


    public interface OnClickListener {
        void OnSureClick();

        void OnCancleClick();
    }

    public AlertDialogUtil setOnClickListener(OnClickListener listener) {
        this.clickListener = listener;
        return this;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dialog != null) {
                dialog.dismiss();
            }
            switch (v.getId()) {
                case R.id.dialog_sure:
                    clickListener.OnSureClick();
                    break;
                case R.id.dialog_cancel:
                    clickListener.OnCancleClick();
                    break;
            }
        }
    };
}
