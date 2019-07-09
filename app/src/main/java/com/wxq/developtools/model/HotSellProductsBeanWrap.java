package com.wxq.developtools.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public  class HotSellProductsBeanWrap implements MultiItemEntity {

  public List<HotSellProductsBean> list;
    @Override
    public int getItemType() {
        return 2;
    }
}