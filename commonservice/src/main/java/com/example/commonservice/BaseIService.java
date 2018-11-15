package com.example.commonservice;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/15
 * desc:
 * version:1.0
 */
public interface BaseIService<T> extends IProvider {

    void setDataBack(T t);

    void getDataBack(ServiceCallBack<T> serviceCallBack);





}
