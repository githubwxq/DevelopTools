package com.example.inject_compiler;

import javax.lang.model.type.TypeMirror;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/28
 * desc: 表示含有bindview 类型的对象封装
 * version:1.0
 */
public class FieldViewBinding {
    private String name; //textview 类型的成员变量名
    private TypeMirror type; //类型
    private int resId; //id

    public String getName() {
        return name;
    }

    public FieldViewBinding setName(String name) {
        this.name = name;
        return this;
    }

    public TypeMirror getType() {
        return type;
    }

    public FieldViewBinding setType(TypeMirror type) {
        this.type = type;
        return this;
    }

    public int getResId() {
        return resId;
    }

    public FieldViewBinding setResId(int resId) {
        this.resId = resId;
        return this;
    }

    public FieldViewBinding(String name, TypeMirror type, int resId) {
        this.name = name;
        this.type = type;
        this.resId = resId;
    }
}
