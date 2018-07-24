package com.trs.aiweishi.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppAplication;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.di.component.ActivityComponent;
import com.trs.aiweishi.di.component.DaggerActivityComponent;
import com.trs.aiweishi.di.module.ActivityModule;
import com.trs.aiweishi.util.DisposedUtil;
import com.trs.aiweishi.view.IBaseView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView{
    private Unbinder unBinder;
    protected SPUtils spUtils;
    protected MDialogConfig config;

    @BindView(R.id.tv_toolbar_name)
    TextView tvToolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        unBinder = ButterKnife.bind(this);
        ToastUtils.setBgColor(getResources().getColor(R.color.color_black_trans));
        spUtils = SPUtils.getInstance(AppConstant.SP_NAME);
        config = new MDialogConfig.Builder()
                .isCanceledOnTouchOutside(true)
                .build();

        initComponent();
        initListener();
        initData();

        tvToolbarName.setText(initToolBarName());
        checkPerssion();
    }

    protected String initToolBarName() {
        return "";
    }

    private void checkPerssion() {
        if (!PermissionUtils.isGranted(AppConstant.READ_PHONE_STATE
                , AppConstant.WRITE_EXTERNAL_STORAGE
                , AppConstant.ACCESS_COARSE_LOCATION
                , AppConstant.READ_EXTERNAL_STORAGE
                , AppConstant.ACCESS_WIFI_STATE
                , AppConstant.CALL_PHONE)) {
            PermissionUtils.permission(AppConstant.READ_PHONE_STATE,
                    AppConstant.WRITE_EXTERNAL_STORAGE
                    ,AppConstant.ACCESS_COARSE_LOCATION
                    ,AppConstant.READ_EXTERNAL_STORAGE
                    ,AppConstant.ACCESS_WIFI_STATE
                    ,AppConstant.CALL_PHONE
            ).callback(new PermissionUtils.FullCallback() {
                @Override
                public void onGranted(List<String> permissionsGranted) {
//                    for (String perssion : permissionsGranted)
//                        LogUtils.dTag(Logger.LOG_FLAG,perssion);
                }

                @Override
                public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
//                    for (String perssion : permissionsDenied)
//                        LogUtils.dTag(Logger.LOG_FLAG,perssion);
                }
            }).request();
        }
    }

    protected void initComponent() {
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(AppAplication.getAppComponent(this))
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void initData();

    protected void initListener() {
    }

    public abstract int initLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
        DisposedUtil.getInstance().dispose();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void showError(Throwable e) {
        e.printStackTrace();
//        ToastUtils.showShort(e.getMessage());
    }

}
