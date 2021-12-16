package com.jnu.myapplication;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
 
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private List<String> title;
 
    public MyFragmentPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments,List<String> titles) {
        super(supportFragmentManager);
        fragmentManager = supportFragmentManager;
        fragmentList = fragments;
        title = titles;
 
    }
 
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
 
    @Override
    public int getCount() {
        return fragmentList.size();
    }
 
    @Override
    public CharSequence getPageTitle(int position){
        return title.get(position);
    }
}