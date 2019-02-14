package com.example.bmobim.bean;

import com.juziwl.uilibrary.recycler.IndexBar.bean.BaseIndexPinyinBean;

import java.util.List;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/14
 * desc:
 * version:1.0
 */
public class CityHeardBean extends BaseIndexPinyinBean {

    private List<String> cityList;  //顶部数据
    //悬停ItemDecoration显示的Tag
    private String suspensionTag;


    public CityHeardBean(List<String> cityList, String suspensionTag, String indexBarTag) {
        this.cityList = cityList;
        this.suspensionTag = suspensionTag;
        this.setBaseIndexTag(indexBarTag);
    }

    @Override
    public String getTarget() { //拼音字段对应的熟悉
        return null;
    }

    @Override
    public boolean isNeedToPinyin() {
        return false;
    }

    @Override
    public String getSuspensionTag() {
        return suspensionTag;
    }


}
