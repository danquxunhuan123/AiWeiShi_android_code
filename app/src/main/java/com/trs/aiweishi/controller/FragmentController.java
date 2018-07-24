package com.trs.aiweishi.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trs.aiweishi.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liufan on 2018/5/17.
 */

public class FragmentController {
    private static FragmentController controller;

    private List<Fragment> fragmentList;
    private FragmentManager fm;
    private int containerId;

    public FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        fragmentList = new ArrayList<>();
    }

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    public void addFragment(BaseFragment fragment){
        fragmentList.add(fragment);
    }

    public void initFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        for (int a = 0; a < fragmentList.size(); a++) {
            ft.add(containerId, fragmentList.get(a), String.valueOf(a));
        }
        ft.commit();
    }

    public void showFragment(int position) {
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == position)
                ft.show(fragmentList.get(i));
            else
                ft.hide(fragmentList.get(i));
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragmentList.get(position);
    }

    public void onDestroy() {
        controller = null;
    }

    public void clear() {
        fragmentList.clear();
    }
}
