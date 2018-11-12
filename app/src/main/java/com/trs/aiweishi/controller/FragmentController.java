package com.trs.aiweishi.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseFragment;

/**
 * Created by Liufan on 2018/5/17.
 */

public class FragmentController {
    private static FragmentController controller = null;
    private FragmentManager fragmentManager = null;
    private int containerId;
    private int currentShowIndex;


    private FragmentController(BaseActivity context, int containerId) {
        fragmentManager = context.getSupportFragmentManager();
        this.containerId = containerId;
    }

    public static FragmentController getInstance(BaseActivity context, int containerId) {
        if (controller == null) {
            controller = new FragmentController(context, containerId);
        }
        return controller;
    }

    public void addFragment(BaseFragment fragment, int finalA) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment, String.valueOf(finalA));
        ft.commit();
        currentShowIndex = finalA;
    }

    public void showFragment(int position) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment findFragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        ft.show(findFragment);
        ft.commitAllowingStateLoss();
        currentShowIndex = position;
    }

    public void hideFragment(int position) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment findFragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        ft.hide(findFragment);
        ft.commitAllowingStateLoss();
    }

    public int getCurrentShowIndex() {
        return currentShowIndex;
    }

    public void setCurrentShowIndex(int currentShowIndex) {
        this.currentShowIndex = currentShowIndex;
    }

    public void onDestroy() {
        controller = null;
    }
}
