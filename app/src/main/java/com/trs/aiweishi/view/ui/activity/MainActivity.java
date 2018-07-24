package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.HomeBean;
import com.trs.aiweishi.controller.FragmentController;
import com.trs.aiweishi.controller.FragmentSelector;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.view.ui.fragment.DocFragment;
import com.trs.aiweishi.view.ui.fragment.HomeFragment;
import com.trs.aiweishi.view.ui.fragment.NgoFragment;
import com.trs.aiweishi.view.ui.fragment.UserFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Liufan on 2018/5/17.
 */
public class MainActivity extends BaseActivity{
    @Inject
    IHomePresenter homeFragPresenter;

    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private FragmentController controller;
    private long mExitTime = 0;

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        controller = FragmentController.getInstance(this, R.id.fl_content);
        FragmentSelector.setSelect(controller, llBottom, tvHome);
        homeFragPresenter.getHomeData(AppConstant.BASE_URL_WCM);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

//    public void toSearch(View view) {
//        startActivity(new Intent(this,SearchActivity.class));
//    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        HomeBean bean = (HomeBean) baseBean;

        controller.clear();
        controller.addFragment(HomeFragment.newInstance(bean.getChannel_list().get(0)));
        controller.addFragment(DocFragment.newInstance(bean.getChannel_list().get(1)));
        controller.addFragment(NgoFragment.newInstance(bean.getChannel_list().get(2)));
        controller.addFragment(UserFragment.newInstance("", ""));
        controller.initFragment();
        controller.showFragment(0);
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

//    private fun showUpdate() {
//        if (!update!!.title.equals(CommonUtil.getVersionName(this@MainActivity))) {
//            AlertDialog.Builder(this@MainActivity).setTitle("有新的版本，是否升级？")
//                    .setIcon(android.R.drawable.ic_dialog_info)
//                    .setPositiveButton("去升级") { _, _ ->
//                    // 点击“确认”后的操作
//                    val uri = Uri.parse(update?.url)
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                startActivity(intent)
//            }
//                    .setNegativeButton("取消") { _, _ ->
//                // 点击“返回”后的操作,这里不设置没有任何操作
//            }.show()
//        }
//    }
}
