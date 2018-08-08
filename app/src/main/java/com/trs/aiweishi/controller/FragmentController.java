package com.trs.aiweishi.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trs.aiweishi.base.BaseFragment;

/**
 * Created by Liufan on 2018/5/17.
 */

public class FragmentController {
    private static FragmentController controller;
    private Fragment currentFragment = null;
    private int count = 0;
    private int currentShowIndex = 0;

    private FragmentController() {
    }

    public static FragmentController getInstance() {
        if (controller == null) {
            controller = new FragmentController();
        }
        return controller;
    }

    public void addFragment(BaseFragment fragment, int containerId, FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(containerId, fragment, String.valueOf(count));

        if (count == 0) {
            ft.show(fragment);
            currentFragment = fragment;
        } else
            ft.hide(fragment);
        ft.commit();
        count++;
    }

    public void showFragment(int position, FragmentManager manager) {
        if (currentShowIndex == position)
            return;

        FragmentTransaction ft = manager.beginTransaction();
        Fragment findFragment = manager.findFragmentByTag(String.valueOf(position));
        ft.show(findFragment);
        ft.hide(currentFragment);
        ft.commitAllowingStateLoss();
        currentFragment = findFragment;
        currentShowIndex = position;
    }

    public Fragment getFragment(int position, FragmentManager manager) {
        return manager.findFragmentByTag(String.valueOf(position));
    }

    public void onDestroy() {
        controller = null;
    }
}
