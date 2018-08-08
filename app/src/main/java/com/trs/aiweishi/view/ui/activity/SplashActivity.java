package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;

import com.blankj.utilcode.util.PermissionUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.brocast.NetWorkReceiver;

import java.util.List;

public class SplashActivity extends BaseActivity {

    private NetWorkReceiver netWorkReceiver;
    private final int delayMillis = 2000;

    @Override
    protected void initData() {
        registReceiver();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, delayMillis);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_splash;
    }

    private void registReceiver() {
        netWorkReceiver = new NetWorkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkReceiver, filter);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkReceiver);
    }
}
