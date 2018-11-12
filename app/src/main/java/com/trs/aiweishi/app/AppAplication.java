package com.trs.aiweishi.app;

import android.app.Application;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.di.component.AppComponent;
import com.trs.aiweishi.di.component.DaggerAppComponent;
import com.trs.aiweishi.di.module.AppModule;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

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
    }

    private void UMConfig() {
        UMConfigure.setLogEnabled(true); //设置LOG开关，默认为false
        UMConfigure.init(this, "5b480776b27b0a69220000bd"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wx58263f2fd876c5eb", "88ad98ed7d76259c0fb0afc4f83b3d39");
        PlatformConfig.setQQZone("1106921261", " rtpnE4i4o3Uyvm6w");
        PlatformConfig.setSinaWeibo("2768991115", "bfcb07caf4a78edd1071f1acc86beacb", "http://www.pmph.com/");  //866875922   48121c73af937b29fe262325442467d3
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
