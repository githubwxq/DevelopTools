package com.wxq.commonlibrary.imageloader.policy;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:倒叙加载
 * version:1.0
 */
public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo()-request1.getSerialNo();
    }
}
