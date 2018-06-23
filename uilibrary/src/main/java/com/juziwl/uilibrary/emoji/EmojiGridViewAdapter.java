package com.juziwl.uilibrary.emoji;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.juziwl.uilibrary.R;

import java.util.ArrayList;

/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月29日
 * @description 表情面板的适配器
 */
public class EmojiGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int itemWidth = 0;
    private ArrayList<String> emojiName = null;
    private ArrayMap<String, Integer> map = new ArrayMap<>();
    int screenWidth;
    int everyitem;

    public EmojiGridViewAdapter(Context mContext, int itemWidth,
                                ArrayList<String> emojiName, ArrayMap<String, Integer> map) {
        this.mContext = mContext;
        this.itemWidth = itemWidth;
        this.emojiName = emojiName;
        this.map = map;
        screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        everyitem = screenWidth > 720 ? 0 : 1; // 0 代表每行8个 则有24个 1代表每行7个 21个

    }

    public ArrayList<String> getEmojiName() {
        return emojiName;
    }

    // 大小固定
    @Override
    public int getCount() {
        if (everyitem == 0) {
            return 24;
        }
        if (everyitem == 1) {
            return 21;
        }
        return 24;
//        return emojiName.size()+1;
    }

    @Override
    public String getItem(int position) {
        return emojiName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img = new ImageView(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemWidth, itemWidth);
        img.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        img.setLayoutParams(params);
        if (position == getCount() - 1) {
            img.setImageResource(R.mipmap.face_delete);
        } else {
            if (position <= emojiName.size() - 1) {
                img.setImageResource(map.get(emojiName.get(position)));
            } else {
                img.setImageResource(R.color.transparent);
            }
//
//            img.setImageResource(map.get(emojiName.get(position)));
        }
        return img;
    }

}
