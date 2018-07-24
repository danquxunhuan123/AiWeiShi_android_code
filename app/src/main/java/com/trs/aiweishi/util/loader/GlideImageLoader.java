package com.trs.aiweishi.util.loader;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Liufan on 2018/5/17.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        ImageView imageView = new ImageView(context);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                200, ViewGroup.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(params);
//        return super.createImageView(context);
//    }
}
