package com.trs.aiweishi.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.trs.aiweishi.R;

/**
 * Created by Liufan on 2018/7/10.
 */

public class AlertDialogUtil {
    private static AlertDialogUtil instance = null;
    private AlertDialog.Builder builder;
    private Context context;
    private View view;
    private Dialog dialog;
    private DialogClickListener dialogListener;
    private EditText content;

    public static AlertDialogUtil getInstance(Context context) {
        if (instance == null) {
            instance = new AlertDialogUtil(context);
        }
        return instance;
    }

    public AlertDialogUtil(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    public void create() {
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
//        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public AlertDialogUtil setContentView(int layoutId) {
        //R.layout.editer_dialog_layout
        view = LayoutInflater.from(context).inflate(layoutId, null);
        view.findViewById(R.id.dialog_btn_sure).setOnClickListener(listener);
        view.findViewById(R.id.dialog_btn_cancel).setOnClickListener(listener);
        builder.setView(view);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        return this;
    }

    public AlertDialogUtil setEditContent(String text) {
        content = view.findViewById(R.id.dialog_content);
        content.setText(text);
        return this;
    }

    public AlertDialogUtil setDialogClickListener(DialogClickListener listener) {
        this.dialogListener = listener;
        return this;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dialog != null) {
                dialog.dismiss();
            }
            switch (v.getId()) {
                case R.id.dialog_btn_sure:
                    dialogListener.OnSureClick(content.getText().toString().trim());
                    break;
                case R.id.dialog_btn_cancel:
                    dialogListener.OnCancleClick();
                    break;
            }
        }
    };

    public void onDestory(){
        context = null;
        instance = null;
    }

    public interface DialogClickListener {
        void OnSureClick(String content);

        void OnCancleClick();
    }

}
