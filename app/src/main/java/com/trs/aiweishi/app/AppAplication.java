package com.trs.aiweishi.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.trs.aiweishi.R;
import com.lf.http.bean.ListData;
import com.trs.aiweishi.dagger.component.AppComponent;
import com.trs.aiweishi.dagger.component.DaggerAppComponent;
import com.trs.aiweishi.dagger.module.AppModule;
import com.trs.aiweishi.util.Logger;
import com.trs.aiweishi.view.ui.activity.DetailActivity;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;
import com.umeng.socialize.PlatformConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Liufan on 2018/5/16.
 */

public class AppAplication extends Application {
    private static volatile AppComponent appComponent;
    private static AppAplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this); //utils

//        initLeakCanery();
        ToastUtils.setBgResource(R.drawable.toast_bg);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
//        LogUtils.getConfig().setLogSwitch(false); //log开关   false关闭
        UMConfig();//UM配置
        baiDuLocationConfig();
        bugly();
    }

    private void bugly() {
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//        strategy.setAppReportDelay(20000);   //改为20s
        CrashReport.initCrashReport(getApplicationContext(), "dc4d5b1469", false);
    }

    private void baiDuLocationConfig() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void UMConfig() {
        UMConfigure.setLogEnabled(true); //设置LOG开关，默认为false
        UMConfigure.init(this
                , "5b480776b27b0a69220000bd"  //Appkey
                , "umeng"  //渠道名称；
                , UMConfigure.DEVICE_TYPE_PHONE //设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
                , "c4f47dc358225de281c00faf90513875"); //Umeng Message Secret
        PlatformConfig.setWeixin("wx58263f2fd876c5eb", "88ad98ed7d76259c0fb0afc4f83b3d39");
        PlatformConfig.setQQZone("1106921261", " rtpnE4i4o3Uyvm6w");
        PlatformConfig.setSinaWeibo("2768991115", "bfcb07caf4a78edd1071f1acc86beacb"
                , "http://www.pmph.com/");  //866875922   48121c73af937b29fe262325442467d3

        //推送
        PushAgent mPushAgent = PushAgent.getInstance(this);//获取消息推送代理示例
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                //AkFM82oaS2Zl9eREYQfGhO84fCUYp1PY-MfyoXMgIL2g   nova2
                //Ag0CfkxWwMUnjst7vWF9i7qtAo5WDDfwnTWhq-kzfk4V  荣耀
                LogUtils.dTag(Logger.LOG_FLAG, "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.dTag(Logger.LOG_FLAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        /**
         * 自定义通知栏打开动作
         * 注意：
         * 请在自定义Application中调用此接口，如果在Activity中调用，当应用进程关闭情况下，设置无效；
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，因此若需启动Activity，
         * 需为Intent添加Flag：Intent.FLAG_ACTIVITY_NEW_TASK，否则无法启动Activity。
         */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
            }

            @Override
            public void openUrl(Context context, UMessage uMessage) {
                super.openUrl(context, uMessage);
            }

            @Override
            public void openActivity(Context context, UMessage uMessage) {
//                super.openActivity(context,uMessage);
                try {
                    JSONObject jsonPush = new JSONObject(uMessage.extra);
                    ListData bean = new ListData();
                    bean.setCname(jsonPush.getString("cname"));
                    bean.setTitle(jsonPush.getString("title"));
                    bean.setUrl(jsonPush.getString("url"));
                    bean.setArticletype(jsonPush.getString("articletype"));
                    bean.setVideourl(jsonPush.getString("videourl"));
                    bean.setDocid(jsonPush.getString("docid"));

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.TITLE_NAME, bean.getCname());
                    intent.putExtra(DetailActivity.PARCELABLE, (Parcelable) bean);
                    intent.putExtra(DetailActivity.URL, bean.getUrl());
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
//                for (Map.Entry entry : msg.extra.entrySet()) {
//                    LogUtils.dTag(Logger.LOG_FLAG, "key-------->  " + entry.getKey());
//                    LogUtils.dTag(Logger.LOG_FLAG, "value-------->  " + entry.getValue());
//                }
//                LogUtils.dTag(Logger.LOG_FLAG, " msg.extra-------->  " +  msg.extra);
//                LogUtils.dTag(Logger.LOG_FLAG, "msg.custom-------->  " + msg.custom);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //添加标签 示例：将“标签1”、“标签2”绑定至该设备
        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {

            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                LogUtils.dTag(Logger.LOG_FLAG, "添加标签isSuccess：-------->  " + isSuccess);
            }

        }, "tag_test");
        //删除标签,将之前添加的标签中的一个或多个删除
//        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
//
//            @Override
//            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
//
//            }
//
//        }, "标签1", "标签2");
        //获取服务器端的所有标签
        mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {

            @Override
            public void onMessage(boolean isSuccess, List<String> result) {
                LogUtils.dTag(Logger.LOG_FLAG, "获取标签isSuccess：-------->  " + isSuccess);
                for (String tag : result){
                    LogUtils.dTag(Logger.LOG_FLAG, "tag：-------->  " + tag);
                }
            }

        });
    }

//    private void initLeakCanery() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//    }

    public static AppAplication getInstance() {
        return instance;
    }

    public static synchronized AppComponent getAppComponent(FragmentActivity baseActivity) {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance, baseActivity))
                    .build();
        }
        return appComponent;
    }
}
