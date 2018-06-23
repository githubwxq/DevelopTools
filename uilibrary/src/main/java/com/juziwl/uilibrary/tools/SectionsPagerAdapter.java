package com.juziwl.uilibrary.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * viewPage adapter
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private List<Fragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments, String... title) {
        super(fm);
        this.title = title;
        this.fragments = fragments;
    }


    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);

        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null && title.length != 0) {
            return title[position];
        } else {
            return fragments.get(position).getTag().toLowerCase();
        }
    }
}