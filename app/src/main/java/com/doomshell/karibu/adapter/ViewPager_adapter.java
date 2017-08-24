package com.doomshell.karibu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anuj on 12/10/2016.
 */

public class ViewPager_adapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPager_adapter(FragmentManager fm) {
        super(fm);
       }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
      }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }


    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitleList.get(position);
    }
}
