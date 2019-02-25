package com.trs.aiweishi.brocast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.trs.aiweishi.view.ui.activity.CheckActivity;

public class LocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CheckActivity activity = (CheckActivity) ActivityUtils.getActivityList().get(1);
        activity.getLocationManager().startLocation();
    }
}
