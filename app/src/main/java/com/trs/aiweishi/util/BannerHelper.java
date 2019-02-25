package com.trs.aiweishi.util;

import android.content.Context;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.lf.http.bean.ListData;
import com.trs.aiweishi.listener.CustPagerTransformer;
import com.trs.aiweishi.util.loader.GlideImageLoader;
import com.trs.aiweishi.view.custview.MyBanner;
import com.trs.aiweishi.view.custview.MyBanner_1;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by Liufan on 2018/5/18.
 */

public class BannerHelper {
    public static void initBannner(Context context, Banner banner, List<String> images) {
        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth();
        params.height = params.width * 9 / 16;
        banner.setLayoutParams(params);

        banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setPageTransformer(false, new CustPagerTransformer(context))
                .setImageLoader(new GlideImageLoader())
                //设置图片集合
                .setImages(images)
                .setOffscreenPageLimit(2)
                //设置banner动画效果
//                .setBannerAnimation(Transformer.DepthPage)
                //设置标题集合（当banner样式有显示title时）
//                .setBannerTitles(titles)
                //设置自动轮播，默认为true
                .isAutoPlay(false)
                //设置轮播时间
//                .setDelayTime(3000)
                //设置指示器位置（当banner模式中有指示器时）
                .setIndicatorGravity(BannerConfig.CENTER)
                //banner设置方法全部调用完毕时最后调用
                .start();
    }

    public static void setConvenientBanner(MyBanner banner, List<ListData> bannerData, MyBanner.OnItemClickListener listener){
        if (!banner.isStarting())
            banner.setDelayTime(3000)
//                    .setPageTransformer(false, new CustPagerTransformer(context))
                    .setOffscreenPageLimit(1)
                    .isAutoPlay(true)
                    .setImages(bannerData)
                    .setOnItemClickListener(listener)
                    .start();
    }

    public static void setConvenientBanner1(MyBanner_1 banner, List<ListData> bannerData, MyBanner_1.OnItemClickListener listener){
        if (!banner.isStarting())
            banner.setDelayTime(3000)
//                    .setPageTransformer(false, new CustPagerTransformer(context))
                    .setOffscreenPageLimit(1)
                    .isAutoPlay(true)
                    .setImages(bannerData)
                    .setOnItemClickListener(listener)
                    .start();
    }

}
