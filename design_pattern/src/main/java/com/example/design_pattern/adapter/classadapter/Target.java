package com.example.design_pattern.adapter.classadapter;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/30
 * desc:
 * version:1.0
 */
public interface Target {

    /**
     * 这是源类Adaptee也有的方法
     */
    public void sampleOperation1();
    /**
     * 这是源类Adapteee没有的方法
     */
    public void sampleOperation2();

}
