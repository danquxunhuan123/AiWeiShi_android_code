package com.trs.aiweishi.view.custview;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.activity.SearchActivity;
import com.youth.banner.BannerConfig;
import com.youth.banner.WeakHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liufan on 2018/5/30.
 */

public class MyBanner_1 extends FrameLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private LinearLayout indicator;
    private TextView title;
    private TextView search;
    private List<View> imageViews;
    private MyPagerAdapter adapter;
    private Context context;
    private int delayTime = BannerConfig.TIME;
    private int currentItem = 0;
    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean isStarting;
    private WeakHandler handler = new WeakHandler();
    private List<ListData> data;
    private int scaleType = 6;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private int count = 0;
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                currentItem = currentItem + 1;
//                Log.i(tag, "curr:" + currentItem + " count:" + count);
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };


    public MyBanner_1(@NonNull Context context) {
        this(context, null);
    }

    public MyBanner_1(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBanner_1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageViews = new ArrayList<>();
        this.context = context;
        handleTypedArray(context, attrs);
        initView(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.youth.banner.R.styleable.Banner);
        scaleType = typedArray.getInt(com.youth.banner.R.styleable.Banner_image_scale_type, scaleType);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout_1, this, true);
        viewPager = view.findViewById(R.id.viewpager);
        title = view.findViewById(R.id.tv_banner_title);
        indicator = view.findViewById(R.id.ll_indicator);
        search = view.findViewById(R.id.tv_search);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    private void setData() {
        search.setOnClickListener(this);
        if (adapter == null) {
            adapter = new MyPagerAdapter();
            viewPager.addOnPageChangeListener(this);
        }
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(SizeUtils.dp2px(10));
        viewPager.setCurrentItem(imageViews.size() * 100);
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth();
        params.height = params.width * 9 / 16;
        viewPager.setLayoutParams(params);

        if (isAutoPlay)
            startAutoPlay();
    }

    public MyBanner_1 setOffscreenPageLimit(int limit) {
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    public MyBanner_1 setPagerMargin(float margin) {
        if (viewPager != null) {
            viewPager.setPageMargin(SizeUtils.dp2px(margin));
        }
        return this;
    }

    public MyBanner_1 setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        if (viewPager != null) {
            viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        }
        return this;
    }

    public MyBanner_1 setImages(List<ListData> data) {
        this.data = data;
        return this;
    }

    private void setImageList() {
        indicator.removeAllViews();
        imageViews.clear();
        count = data.size();
        if (data == null || data.size() <= 0) {
            return;
        }

        for (int i = 0; i < data.size(); i++) {
            RoundImageView imageView = new RoundImageView(context);
            //imageview
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(finalI);
                }
            });
            imageView.setCurrMode(RoundImageView.MODE_ROUND);
            setScaleType(imageView);
            GlideUtils.loadUrlImg(context, data.get(i).getImages().get(0).getSrc(), imageView);

            //indicator
            View point = new View(context);
            point.setBackgroundColor(Color.parseColor("#75787b"));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    SizeUtils.dp2px(20), SizeUtils.dp2px(5));
            param.leftMargin = 10;
            point.setLayoutParams(param);
            indicator.addView(point);

            imageViews.add(imageView);
        }

        title.setText(data.get(0).getTitle());
        indicator.getChildAt(0).setBackgroundColor(Color.parseColor("#697cf4"));
    }

    private void setScaleType(ImageView view) {
        switch (scaleType) {
            case 0:
                view.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case 1:
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case 2:
                view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case 3:
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 4:
                view.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case 5:
                view.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case 6:
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 7:
                view.setScaleType(ImageView.ScaleType.MATRIX);
                break;
        }
    }

    public MyBanner_1 start() {
        isStarting = true;
        setImageList();
        setData();
        return this;
    }

    public boolean isStarting() {
        return isStarting;
    }

    public void setStarting(boolean starting) {
        isStarting = starting;
    }

    public MyBanner_1 isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    public MyBanner_1 setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    private OnItemClickListener listener;

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context,SearchActivity.class));
    }

    public interface OnItemClickListener {
        //        void OnItemClick(String img);
        void OnItemClick(int position);
    }

    public MyBanner_1 setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void releaseBanner() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        int po = position % imageViews.size();
        title.setText(data.get(po).getTitle());

        for (int i = 0; i < indicator.getChildCount(); i++) {
            if (i == po)
                indicator.getChildAt(i).setBackgroundColor(Color.parseColor("#697cf4"));
            else
                indicator.getChildAt(i).setBackgroundColor(Color.parseColor("#75787b"));
        }

        currentItem = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
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
//            container.removeView((View) object);
        }
    }
}
