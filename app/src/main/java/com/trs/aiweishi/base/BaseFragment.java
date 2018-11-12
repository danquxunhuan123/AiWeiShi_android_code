package com.trs.aiweishi.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.trs.aiweishi.app.AppAplication;
import com.trs.aiweishi.di.component.DaggerFragmentComponent;
import com.trs.aiweishi.di.component.FragmentComponent;
import com.trs.aiweishi.di.module.FragmentModule;
import com.trs.aiweishi.util.DisposedUtil;
import com.trs.aiweishi.view.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liufan on 2018/5/17.
 */
public abstract class BaseFragment extends Fragment implements IBaseView{

    protected Object mParam1;
    protected Object mParam2;

    protected FragmentActivity context;
    private Unbinder bind;
    protected MDialogConfig config;
//    private View contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if (contentView != null) {
//            ViewGroup parent = (ViewGroup) contentView.getParent();
//            if (parent != null) {
//                parent.removeView(contentView);
//            }
//            return contentView;
//        }
//        return contentView = inflater.inflate(initLayout(),
//                container, false);

        View inflate = inflater.inflate(initLayout(), container, false);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        context = getActivity();
        config = new MDialogConfig.Builder()
                .isCanceledOnTouchOutside(true)
                .build();
        initComponent();
        initData();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                    .appComponent(AppAplication.getAppComponent(context))
                    .fragmentModule(new FragmentModule(this))
                    .build();
    }

    protected abstract void initComponent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DisposedUtil.getInstance().dispose();
    }

    @Override
    public void showError(Throwable e) {
//        e.printStackTrace();
        MProgressDialog.dismissProgress();
        ToastUtils.showShort(e.getMessage());
    }

    public abstract void initData();

    public abstract int initLayout();
}
