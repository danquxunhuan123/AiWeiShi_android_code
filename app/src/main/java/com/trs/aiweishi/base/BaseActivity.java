package com.trs.aiweishi.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
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

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private Unbinder unBinder;
    protected SPUtils spUtils;
    protected MDialogConfig config;

    @BindView(R.id.tv_toolbar_name)
    TextView tvToolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());

        if (isTranslucent())
            setTranslucent(this);

        unBinder = ButterKnife.bind(this);
        checkPerssion();
        initUtil();
        initComponent();
        initListener();
        initData();
        tvToolbarName.setText(initToolBarName());
    }

    private void initUtil() {
        ToastUtils.setBgColor(getResources().getColor(R.color.color_black_trans));
        spUtils = SPUtils.getInstance(AppConstant.SP_NAME);
        config = new MDialogConfig.Builder()
                .isCanceledOnTouchOutside(true)
                .build();
    }

    protected boolean isFitsSystemWindows() {
        return true;
    }

    protected boolean isTranslucent() {
        return true;
    }

    /**
     * 使状态栏透明
     * 适用于图片作为背景的界面，此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    private void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity
                    .findViewById(android.R.id.content)).getChildAt(0);
            if (isFitsSystemWindows()) {
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
    }

    protected void setStatusColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View StatusView = createStatusView(activity, color);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(StatusView);
        }
    }

    private View createStatusView(Activity activity, int color) {
        int statusBarHeight = BarUtils.getStatusBarHeight();
        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(AppAplication.getAppComponent(this))
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract int initLayout();

    protected abstract void initData();

    protected void initComponent() {
    }

    protected void initListener() {
    }

    protected String initToolBarName() {
        return "";
    }

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
    public void showSuccess(BaseBean baseBean) {

    }

    @Override
    public void showError(Throwable e) {
        e.printStackTrace();
//        ToastUtils.showShort(e.getMessage());
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
                    , AppConstant.ACCESS_COARSE_LOCATION
                    , AppConstant.READ_EXTERNAL_STORAGE
                    , AppConstant.ACCESS_WIFI_STATE
                    , AppConstant.CALL_PHONE
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

}
