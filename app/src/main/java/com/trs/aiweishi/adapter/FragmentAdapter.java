package com.trs.aiweishi.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.trs.aiweishi.base.BaseFragment;

import java.util.List;

/**
 * Created by Liufan on 2018/6/28.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter{
    private List<BaseFragment> fragments;
    private List<String> titles;
    public FragmentAdapter(FragmentManager fm, List<BaseFragment> list, List<String> titles) {
        super(fm);
        fragments = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void update(List<BaseFragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
