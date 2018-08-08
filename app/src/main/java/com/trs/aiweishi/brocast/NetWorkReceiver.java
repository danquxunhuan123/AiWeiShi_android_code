package com.trs.aiweishi.brocast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;

public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkUtils.isConnected())
            ToastUtils.showShort(context.getResources().getString(R.string.net_error));
    }
}
