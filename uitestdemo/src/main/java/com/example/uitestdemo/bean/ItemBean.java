package com.example.uitestdemo.bean;

import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.List;

/**
 * 每个功能名称
 */
public class ItemBean  implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemBean(String name) {
        this.name = name;
    }

    private  String  name;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private Fragment fragment;


    private  int  icon;

    public ItemBean(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }







}
