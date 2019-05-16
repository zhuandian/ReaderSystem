package com.zhuandian.bookreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhuandian.base.BaseFragment;

import java.util.List;

/**
 * desc :
 * author：xiedong
 * date：2019/4/19
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    List<BaseFragment> fragments;

    public HomePageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
