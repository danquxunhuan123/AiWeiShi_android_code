package com.trs.aiweishi.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lf.http.bean.BaseBean;
import com.lf.http.view.IBaseView;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppAplication;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.dagger.component.ActivityComponent;
import com.trs.aiweishi.dagger.component.DaggerActivityComponent;
import com.trs.aiweishi.dagger.module.ActivityModule;
import com.lf.http.utils.DisposedUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private Unbinder unBinder;
    protected SPUtils spUtils;
    public MDialogConfig config;

    @BindView(R.id.tv_toolbar_name)
    TextView tvToolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isSetContentView()) {
            setContentView(initLayout());
            unBinder = ButterKnife.bind(this);
            tvToolbarName.setText(initToolBarName());
        }

        if (isTranslucent())
            setTranslucent(this);

        PushAgent.getInstance(this).onAppStart();
        initUtil();
        initComponent();
        initListener();

        config = new MDialogConfig.Builder()
                .isCanceledOnTouchOutside(true)
                .build();

        if (savedInstanceState != null)
            initData(savedInstanceState);
        else
            initData();
    }

    protected void initData(Bundle savedInstanceState) {
    }

    private void initUtil() {
        ToastUtils.setBgColor(getResources().getColor(R.color.color_black_trans));
        spUtils = SPUtils.getInstance(AppConstant.SP_NAME);
    }

    /**
     * 是否界面留出padding，从状态栏下方开始绘制
     *
     * @return 默认返回true，从状态栏下方开始绘制，界面不被状态栏遮挡
     * 返回false，不流出padding，从屏幕顶部开始绘制界面
     */
    protected boolean isFitsSystemWindows() {
        return true;
    }

    /**
     * 是否要状态栏透明
     *
     * @return 默认true，设置透明  f返回alse，不设置状态栏透明
     */
    protected boolean isTranslucent() {
        return true;
    }

    protected boolean isSetContentView() {
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
                if (rootView != null) {
                    rootView.setFitsSystemWindows(true);
                    rootView.setClipToPadding(true);
                }
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
        if (unBinder != null)
            unBinder.unbind();
        DisposedUtil.getInstance().dispose();
        super.onDestroy();
    }

    public void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    public void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
    }

    @Override
    public void showError(Throwable e) {
        MProgressDialog.dismissProgress();
        e.printStackTrace();
    }

}
