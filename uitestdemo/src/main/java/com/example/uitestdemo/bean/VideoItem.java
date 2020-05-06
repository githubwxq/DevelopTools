package com.example.uitestdemo.bean;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;
import com.example.uitestdemo.bean.PostItem;

public class VideoItem extends PostItem {

    @Override
    public int getItemType() {
        return R.layout.choose_item;
    }


    @Override
    public void bindViewWidthData(BaseViewHolder helper) {

    }
}
