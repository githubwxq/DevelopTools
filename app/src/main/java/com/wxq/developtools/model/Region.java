package com.wxq.developtools.model;

import com.wxq.commonlibrary.util.StringUtils;

//大洋洲，欧洲，东南亚等
public class Region {


    public Region(String id, String name, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    /**
     * id :
     * name :
     */

    public String id;
    public String name;

    public boolean isSelect;

    public String getName() {
        if (StringUtils.isEmpty(name)){
            return "全部";
        }else {
            return name;
        }

    }
}
