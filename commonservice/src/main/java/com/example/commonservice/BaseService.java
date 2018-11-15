package com.example.commonservice;

import android.content.Context;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/15
 * desc:
 * version:1.0
 */
public class BaseService<T> implements BaseIService<T> {
    public ServiceCallBack serviceCallBack;
    public  Context mContext;
    @Override
    public void setDataBack(T t) {
        if (null != serviceCallBack) {
            serviceCallBack.callBack(t);
        }
    }

    @Override
    public void getDataBack(ServiceCallBack serviceCallBack) {
        this.serviceCallBack = serviceCallBack;
    }

    @Override
    public void init(Context context) {
        mContext=context;
    }
}
