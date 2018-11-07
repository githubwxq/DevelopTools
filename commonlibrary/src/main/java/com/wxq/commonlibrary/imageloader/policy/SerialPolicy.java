package com.wxq.commonlibrary.imageloader.policy;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:顺序加载
 * version:1.0
 */
public class SerialPolicy implements LoadPolicy {


    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo()-request2.getSerialNo();
    }
}
