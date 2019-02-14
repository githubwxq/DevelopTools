package com.example.bmobim.bean;

import com.juziwl.uilibrary.recycler.IndexBar.bean.BaseIndexPinyinBean;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/13
 * desc:
 * version:1.0
 */
public class CityBeanWithTop extends BaseIndexPinyinBean {

    public String city;//城市名字

    public void setCity(String city) {
        this.city = city;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isTop;//是否是最上面的 不需要被转化成拼音的


    @Override
    public String getTarget() {
        return city;
    }

    @Override
    public boolean isNeedToPinyin() {  //是否需要悬停顶部
        return !isTop;
    }

    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}
