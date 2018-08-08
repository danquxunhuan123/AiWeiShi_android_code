package com.trs.aiweishi.util;

import android.app.Activity;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Liufan on 2018/7/20.
 */

public class UMShareUtil {

    private static UMShareUtil instance = null;

    private UMShareUtil() {
    }

    public static UMShareUtil getInstance() {
        if (instance == null) {
            instance = new UMShareUtil();
        }
        return instance;
    }

    public void share(Activity context, SHARE_MEDIA modeia,String title,String description,
                      String thumbUrl, String url) {
        UMImage thumb;
        if (TextUtils.isEmpty(thumbUrl))
            thumb = new UMImage(context, R.mipmap.ic_launcher);
        else
            thumb = new UMImage(context, thumbUrl);//网络图片
//        UMImage image = new UMImage(ShareActivity.this, file);//本地文件
//        UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
//        UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
//        UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(description);//描述

        new ShareAction(context)
                .setPlatform(modeia)//传入平台
                .withMedia(web)  //分享链接
                .setCallback(shareListener)//回调监听器
                .share();
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            listener.onShareSuccess(platform);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            listener.onShareError(platform, t);
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

    public void onDestory(){
        instance = null;
        listener = null;
    }

    private OnShareSuccessListener listener;

    public UMShareUtil setOnShareSuccessListener(OnShareSuccessListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnShareSuccessListener {
        void onShareSuccess(SHARE_MEDIA platform);

        void onShareError(SHARE_MEDIA platform, Throwable t);
    }
}
