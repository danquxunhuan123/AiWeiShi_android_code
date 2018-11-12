package com.trs.aiweishi.view.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.AdBean;
import com.trs.aiweishi.brocast.NetWorkReceiver;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.ui.fragment.AdDialogFragment;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private NetWorkReceiver netWorkReceiver;
    private AdBean bean;
    private AdDialogFragment adFragment;
    private int what_2 = 2;
    @Inject
    IHomePresenter presenter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: //进入主页
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
                case 2:  //显示广告
                    showAd();
                    break;
                case 3: //点击广告图片跳转
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    Intent intent = new Intent(SplashActivity.this, DetailActivity.class);
//                    intent.putExtra(DetailActivity.URL, spUtils.getString(AppConstant.AD_PIC_URL));
                    intent.putExtra(DetailActivity.TYPE, -1); //广告类型
                    intent.putExtra(DetailActivity.PARCELABLE, (Parcelable) bean);
                    intent.putExtra(DetailActivity.TITLE_NAME,bean.getAdTitle());
                    startActivity(intent);

                    finish();
                    break;
            }
        }
    };

    private void showAd() {
        if ("是".equals(spUtils.getString(AppConstant.AD_IS_SHOW))) {
            adFragment = AdDialogFragment.newInstance();
            adFragment.setHandle(handler);
            adFragment.show(getSupportFragmentManager(), AdDialogFragment.TAG);
        }else {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        presenter.getAdData(AppConstant.AD_URL);
        registReceiver();
        checkPerssion();
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        bean = (AdBean) baseBean;
        getNetAdPic();
    }

    private void getNetAdPic() {
        GlideUtils.loadUrlToFile(this, bean.getImageUrl(), new AdDialogFragment.OnPicDownloadListener() {
            @Override
            public void OnPicDownload(Bitmap resource) {
                CacheUtils.getInstance(getExternalCacheDir()).put(AppConstant.AD_PIC_CACHE, resource); //缓存图片
                spUtils.put(AppConstant.AD_FILE_UPDATE_TIME, bean.getUpdateTime()); //更新时间
                spUtils.put(AppConstant.AD_IS_SHOW, bean.getIsShow()); //是否显示
                spUtils.put(AppConstant.AD_PIC_URL, bean.getAdUrl()); //图片跳转地址
            }
        });
    }

    @Override
    public boolean isSetContentView() {
        return false;
    }

    @Override
    public int initLayout() {
        return -1;
    }

    private void registReceiver() {
        netWorkReceiver = new NetWorkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkReceiver, filter);
    }

    private void checkPerssion() {
        if (!PermissionUtils.isGranted(AppConstant.READ_PHONE_STATE
                , AppConstant.WRITE_EXTERNAL_STORAGE
                , AppConstant.ACCESS_COARSE_LOCATION
                , AppConstant.READ_EXTERNAL_STORAGE
                , AppConstant.ACCESS_WIFI_STATE
                , AppConstant.CALL_PHONE)) {
            PermissionUtils.permission(AppConstant.READ_PHONE_STATE
                    , AppConstant.WRITE_EXTERNAL_STORAGE
                    , AppConstant.ACCESS_COARSE_LOCATION
                    , AppConstant.READ_EXTERNAL_STORAGE
                    , AppConstant.ACCESS_WIFI_STATE
                    , AppConstant.CALL_PHONE
            ).callback(new PermissionUtils.FullCallback() {
                @Override
                public void onGranted(List<String> permissionsGranted) {
                    toMainActivity();
                }

                @Override
                public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                    toMainActivity();
                }
            }).request();
        } else {
            toMainActivity();
        }
    }

    public void toMainActivity() {
        handler.sendEmptyMessageDelayed(what_2, 500);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(netWorkReceiver);
        super.onDestroy();
    }
}
