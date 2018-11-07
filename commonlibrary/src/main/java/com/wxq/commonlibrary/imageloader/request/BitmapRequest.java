package com.wxq.commonlibrary.imageloader.request;

import com.wxq.commonlibrary.imageloader.loader.SimpleImageLoader;
import com.wxq.commonlibrary.imageloader.policy.LoadPolicy;

import java.util.Comparator;
import java.util.Objects;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:图片请求
 * version:1.0
 */
public class BitmapRequest implements Comparator<BitmapRequest>{  //排序

//    、、需要的功能 1 加载策略 2

    private LoadPolicy loadPolicy= SimpleImageLoader.getInstance().getConfig().getLoadPolicy();



    private int serialNo;


    @Override
    public int compare(BitmapRequest o1, BitmapRequest o2) {
        //间接 抛给接口比较  接口全局配置的
        return loadPolicy.compareto(o1,o2);
    }


    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }


//    、、重新hascode 何equal方法  contain方法


}
