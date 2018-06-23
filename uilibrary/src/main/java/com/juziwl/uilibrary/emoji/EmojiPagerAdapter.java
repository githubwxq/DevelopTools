package com.juziwl.uilibrary.emoji;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月29日
 * @description 表情面板
 */
public class EmojiPagerAdapter extends PagerAdapter {
    ArrayList<GridView> list= new ArrayList<>();

    public EmojiPagerAdapter(ArrayList<GridView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

}
