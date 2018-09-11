package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.HomeBean;
import com.trs.aiweishi.bean.UpdateBean;
import com.trs.aiweishi.controller.FragmentController;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.AlertDialogUtil;
import com.trs.aiweishi.view.IMainView;
import com.trs.aiweishi.view.ui.fragment.DocFragment;
import com.trs.aiweishi.view.ui.fragment.HomeFragment;
import com.trs.aiweishi.view.ui.fragment.NgoFragment;
import com.trs.aiweishi.view.ui.fragment.UserFragment;
import com.umeng.socialize.UMShareAPI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Liufan on 2018/5/17.
 */
public class MainActivity extends BaseActivity implements IMainView {
    @Inject
    IHomePresenter homeFragPresenter;

    @BindView(R.id.tv_refresh)
    TextView refresh;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private FragmentController controller = null;
    private View lastSelectedText = null;
    private long mExitTime = 0;

    private HomeBean bean = null;

    @Override
    protected boolean isFitsSystemWindows() {
        return false;
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        setSelect();

        homeFragPresenter.updateInfo(AppConstant.UPDATE);
        homeFragPresenter.getHomeData(AppConstant.BASE_URL_WCM);
    }

    private void setSelect() {
        controller = FragmentController.getInstance(this, R.id.fl_content);

        for (int a = 0; a < llBottom.getChildCount(); a++) {
            if (a == 0) {
                setSelectIcon(tvHome);//默认选中首页
            }
            final int finalA = a;
            llBottom.getChildAt(a).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastSelectedText != null) {
                        lastSelectedText.setSelected(false);
                    }

                    setSelectIcon(v);
                    if (controller != null) {
                        controller.showFragment(finalA);
                    }
                }
            });
        }
    }

    private void setSelectIcon(View view) {
        view.setSelected(true);
        lastSelectedText = view;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.tv_refresh)
    public void refresh() {
        refresh.setVisibility(View.GONE);
        homeFragPresenter.getHomeData(AppConstant.BASE_URL_WCM);
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        bean = (HomeBean) baseBean;

        controller.addFragment(HomeFragment.newInstance(bean.getChannel_list().get(0)));
        controller.addFragment(DocFragment.newInstance(bean.getChannel_list().get(1)));
        controller.addFragment(NgoFragment.newInstance(bean.getChannel_list().get(2)));
        controller.addFragment(UserFragment.newInstance("", ""));
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        refresh.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (controller != null)
            controller.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.showShort(getResources().getString(R.string.app_exit));
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void update(final UpdateBean obj) {
        spUtils.put(AppConstant.YINGYONGBAO, obj.getAndroid_url()); //动态记录应用宝地址
//        if (!AppUtils.getAppVersionName().equals(obj.getAndroid_CurrentVersion())) {
//            spUtils.put(AppConstant.IS_UPDATE, true); //版本更新标记，需要更新
//
//            //如果取消过版本更新，就不再提示。
//            boolean update = spUtils.getBoolean(AppConstant.CANCLE_UPDATE, false);
//            if (update)
//                return;
//
//            new AlertDialogUtil(this)
//                    .setDialogView()
//                    .setDialogTitle("版本更新")
//                    .setDialogContent(obj.getAndroid_boxmsg().replace("\\n", "\n"))
//                    .setOnClickListener(new AlertDialogUtil.OnClickListener() {
//                        @Override
//                        public void OnSureClick() {
//                            Uri uri = Uri.parse(obj.getAndroid_url());
//                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
//                        }
//
//                        @Override
//                        public void OnCancleClick() {
//                            spUtils.put(AppConstant.CANCLE_UPDATE, true);
//                        }
//                    })
//                    .create();
//        } else {
//            spUtils.put(AppConstant.IS_UPDATE, false);  //版本更新标记，不需要更新
//        }
    }
}
