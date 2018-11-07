package com.wxq.commonlibrary.imageloader.policy;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:
 * version:1.0
 */
public interface LoadPolicy {

    int compareto(BitmapRequest request1,BitmapRequest request2);


}
