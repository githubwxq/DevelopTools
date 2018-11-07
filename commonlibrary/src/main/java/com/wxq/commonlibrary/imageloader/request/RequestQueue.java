package com.wxq.commonlibrary.imageloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:阻塞队列
 * version:1.0
 */
public class RequestQueue {

    //排序关联 实现优先级 需要实现优先级接口然后queue里面自己会处理的
    private BlockingQueue<BitmapRequest> mRequestQueue=new PriorityBlockingQueue<>();

}
