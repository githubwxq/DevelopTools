package com.example.bmobim.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.juziwl.uilibrary.recycler.IndexBar.bean.BaseIndexPinyinBean;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/14
 * desc:
 * version:1.0
 */
public class CityBean extends BaseIndexPinyinBean implements MultiItemEntity {
    public static final int singleCity = 1;
    public static final int sepecialCity = 2;
    private int itemType;
    public String city;//城市名字

    public CityBean(String city, int itemType) {
        this.itemType = itemType;
        this.city = city;
    }

    @Override
    public String getTarget() {
        return city;
    }


    @Override
    public int getItemType() {
        return itemType;
    }
//
//    @Override
//    public boolean isNeedToPinyin() {
//        if (itemType==1){
//            return true;
//        }else {
//            return false;
//        }
//    }

//    @Override
//    public String getSuspensionTag() {
//        if (itemType==1){
//            return baseIndexTag;
//        }else {
//            return city;
//        }
//    }

    //     this.setBaseIndexTag(indexBarTag);
    @Override
    public boolean isNeedToPinyin() {  //是否需要悬停顶部
        if (itemType == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isShowSuspension() {
        if (itemType == 1) {
            return true;
        } else {
            return false;
        }
    }


}
