package com.example.uitestdemo.bean;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public abstract class PostItem implements MultiItemEntity {




    public abstract void bindViewWidthData(BaseViewHolder helper);
}
