package com.example.forecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CityFragmentAdaptor extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;

    public CityFragmentAdaptor(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {//回傳
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {//回傳資料筆數
        return fragmentList.size();
    }
}
