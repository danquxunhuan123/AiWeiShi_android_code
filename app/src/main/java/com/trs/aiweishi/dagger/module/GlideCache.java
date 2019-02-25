package com.trs.aiweishi.dagger.module;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by Liufan on 2018/7/16.
 */
@GlideModule
public class GlideCache extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setDiskCache(
                new DiskLruCacheFactory( getStorageDirectory(context)+"/GlideDisk", diskCacheSizeBytes )
        );
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    //判断是否有外部路径，有就设置这个路径，没有就用内部路径
    private String getStorageDirectory(Context context){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
    }
}
