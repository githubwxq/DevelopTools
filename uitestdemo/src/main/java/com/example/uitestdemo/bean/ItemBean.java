package com.example.uitestdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 每个功能名称
 */
public class ItemBean  implements Serializable {

    List<ItemBean> list;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ItemBean(String name) {
        this.name = name;
    }

    private  String  name;
    private  String  path;


}
