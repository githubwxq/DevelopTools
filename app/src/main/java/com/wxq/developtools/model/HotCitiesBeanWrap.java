package com.wxq.developtools.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public  class HotCitiesBeanWrap implements MultiItemEntity {

    @Override
    public int getItemType() {
        return 1;
    }

    public  List<HotCitiesBean> hotCitiesBeanList;

}