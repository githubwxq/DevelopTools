package com.wxq.developtools.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class HomePageData {
    public List<HotCitiesBean> hotCities;
    public List<HotSellProductsBean> hotSellProducts;
    public List<RecomentProductsBean> recomentProducts;

    public List<MultiItemEntity> getlist(){
        List<MultiItemEntity> list=new ArrayList<>();
        RecommendBeanWrap recommendBeanWrap=new RecommendBeanWrap();
        recommendBeanWrap.list=recomentProducts;
        list.add(recommendBeanWrap);
        HotCitiesBeanWrap hotCitiesBeanWrap=new HotCitiesBeanWrap();
        hotCitiesBeanWrap.hotCitiesBeanList=hotCities;
        list.add(hotCitiesBeanWrap);
        list.addAll(hotSellProducts);
        return list;
    }

}
