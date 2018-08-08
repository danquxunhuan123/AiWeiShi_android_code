package com.trs.aiweishi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Liufan on 2018/7/26.
 */

public class ImgsAdapter extends PagerAdapter {
    private List<View> imageViews;
    private Context context;

    public ImgsAdapter(Context context, List<View> imageViews) {
        this.imageViews = imageViews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = imageViews.get(position % imageViews.size());
        if (view.getParent() != null) {
            container.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}
