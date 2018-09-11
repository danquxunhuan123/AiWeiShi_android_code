package com.trs.aiweishi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by Liufan on 2018/7/2.
 */

public class GlideUtils
{
    public static void loadCircleBitmapImg(Context context, Bitmap bitmap, ImageView imageView){
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(bitmap)
                .into(imageView);
    }

    public static void loadCircleUrlImg(Context context, String url, ImageView imageView){
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(url)
                .into(imageView);
    }

    public static void loadBitmapImg(Context context, Bitmap bitmap, ImageView imageView){
        Glide.with(context)
                .load(bitmap)
                .into(imageView);
    }

    public static void loadLocalImg(Context context,int id, ImageView imageView){
        Glide.with(context)
                .load(id)
                .into(imageView);
    }

    public static void loadUrlImg(Context context, String url, ImageView imageView){
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.fitCenterTransform())
                .load(url)
                .into(imageView);
    }

    public static void loadUrlImg(Context context, String url, ImageView imageView, RequestListener listener){
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.fitCenterTransform())
                .load(url)
                .listener(listener)
                .into(imageView);
    }

    public static void loadUrlBitmap(String url, ImageView imageView, SimpleTarget<Bitmap> simpleTarget){
        Glide.with(imageView.getContext()).asBitmap().load(url).into(simpleTarget);
    }
}
