package com.example.bmobim.bean;

import android.view.View;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/15
 * desc:
 * version:1.0
 */
public class BuildTest {

    View.OnClickListener listener;
    View.OnClickListener listener2;

    public View.OnClickListener getListener() {
        return listener;
    }

    public BuildTest setListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    public View.OnClickListener getListener2() {
        return listener2;
    }

    public BuildTest setListener2(View.OnClickListener listener2) {
        this.listener2 = listener2;
        return this;
    }

    public String getAge() {
        return age;
    }

    public BuildTest setAge(String age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public BuildTest setName(String name) {
        this.name = name;
        return this;
    }

    public String getHobby() {
        return hobby;
    }

    public BuildTest setHobby(String hobby) {
        this.hobby = hobby;
        return this;
    }

    public String getParent() {
        return parent;
    }

    public BuildTest setParent(String parent) {
        this.parent = parent;
        return this;
    }

    private String age;
    private String name;
    private String hobby;
    private String parent;




}
