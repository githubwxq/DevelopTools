package com.wxq.developtools.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public  class RecommendBeanWrap implements MultiItemEntity {

    @Override
    public int getItemType() {
        return 0;
    }

    public  List<RecomentProductsBean> list;

}