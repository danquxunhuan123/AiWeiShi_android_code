package com.trs.aiweishi.listener;

import android.content.Context;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Liufan on 2018/7/2.
 */

public class MyUMAuthListener implements UMAuthListener {

    public static String uid = "uid";
    public static String openid = "openid";
    public static String unionid = "unionid";
    public static String refreshToken = "refreshToken";
    public static String accessToken = "accessToken";
    public static String access_token = "access_token";
    public static String name = "name";
    public static String screen_name = "screen_name";
    public static String gender = "gender";
    public static String iconurl = "iconurl";

    Context mContext;

    @Inject
    public MyUMAuthListener(Context context) {
        this.mContext = context;
    }

    /**
     * @param platform 平台名称
     * @desc 授权开始的回调
     */
    @Override
    public void onStart(SHARE_MEDIA platform) {
    }

    /**
     * @param platform 平台名称
     * @param action   行为序号，开发者用不上
     * @param data     用户资料返回
     * @desc 授权成功的回调
     */
    @Override
    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
        listener.OnThirdSeccess(data);
//        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = it.next();
//            LogUtils.dTag(AppConstant.LOG_FLAG,entry.getKey());
//        }
    }

    /**
     * @param platform 平台名称
     * @param action   行为序号，开发者用不上
     * @param t        错误原因
     * @desc 授权失败的回调
     */
    @Override
    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * @param platform 平台名称
     * @param action   行为序号，开发者用不上
     * @desc 授权取消的回调
     */
    @Override
    public void onCancel(SHARE_MEDIA platform, int action) {
        Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
    }

    private OnLoginSuccessListener listener;

    public void setOnThirdSucListener(OnLoginSuccessListener listener) {
        this.listener = listener;
    }

    public interface OnLoginSuccessListener {
        void OnThirdSeccess(Map<String, String> data);
    }
}
