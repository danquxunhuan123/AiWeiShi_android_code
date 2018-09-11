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
    private static FragmentController controller;
    private Fragment currentFragment = null;
    private FragmentManager fragmentManager;
    private int containerId;
    private int count = 0;
    private int currentShowIndex = 0;

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

    public void addFragment(BaseFragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment, String.valueOf(count));

        if (count == 0) {
            ft.show(fragment);
            currentFragment = fragment;
        } else
            ft.hide(fragment);

        ft.commit();
        count++;
    }

    public void showFragment(int position) {
        if (currentShowIndex == position)
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment findFragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        ft.show(findFragment);
        ft.hide(currentFragment);
        ft.commitAllowingStateLoss();
        currentFragment = findFragment;
        currentShowIndex = position;
    }

    public void onDestroy() {
        controller = null;
    }
}
