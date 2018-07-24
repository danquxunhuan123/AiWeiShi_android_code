package com.trs.aiweishi.controller;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Liufan on 2018/6/14.
 */

public class FragmentSelector {
    private static View lastSelectedText;

    public static void setSelect(final FragmentController controller, ViewGroup footView, View selectView) {
        for (int a = 0; a < footView.getChildCount(); a++) {
            if (a == 0) {
                setSelectIcon(selectView);//默认选中首页
            }
            final int finalA = a;
            footView.getChildAt(a).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastSelectedText != null) {
                        lastSelectedText.setSelected(false);
                    }

//                    if (finalA == 3)
//                        toolbar.setVisibility(View.GONE);
//                    else
//                        toolbar.setVisibility(View.VISIBLE);

                    setSelectIcon(v);
                    if (controller != null)
                        controller.showFragment(finalA);
                }
            });
        }
    }

    private static void setSelectIcon(View view) {
        view.setSelected(true);
        lastSelectedText = view;
    }
}
