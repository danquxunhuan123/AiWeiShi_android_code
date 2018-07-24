package com.trs.aiweishi.util;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.trs.aiweishi.base.BaseAdapter;
import com.trs.aiweishi.bean.TextDrawableBean;

/**
 * Created by Liufan on 2018/5/18.
 */

public class RecycleviewUtil {

    public static void initGridRecycleView(RecyclerView recyclerView, final BaseAdapter adapter,
                                           Context context, final int spanCount){
        final GridLayoutManager gm = new GridLayoutManager(context, spanCount);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                return itemViewType == TextDrawableBean.TEXT_DRAWABLE_TYPE ? 1 : gm.getSpanCount();
            }
        });
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(adapter);
    }

    public static void initGridNoTypeRecycleView(RecyclerView recyclerView, final BaseAdapter adapter,
                                           Context context, final int spanCount){
        final GridLayoutManager gm = new GridLayoutManager(context, spanCount);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(adapter);
    }

    public static void initLinearRecycleView(RecyclerView recyclerView, final BaseAdapter adapter,
                                             Context context){
        LinearLayoutManager lm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }
}
