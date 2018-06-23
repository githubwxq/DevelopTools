package com.juziwl.uilibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author wy
 * @version V_5.0.0
 * @date 2016/3/1
 * @description viewpage 组合 fragment 的适配器分装
 */
public class ViewPagerFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments; // 每个Fragment对应一个Page
	private String[] TITLES ;
    public ViewPagerFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, String[] titles) {
        super(fragmentManager);
    	this.fragments = fragments;
        TITLES=titles;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
    	return TITLES[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

	@Override
	public Fragment getItem(int arg0) {

        return fragments.get(arg0);
	}



}
