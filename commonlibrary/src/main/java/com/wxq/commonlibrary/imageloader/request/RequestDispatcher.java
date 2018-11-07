package com.wxq.commonlibrary.imageloader.request;

import java.util.concurrent.BlockingQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:转发器请求转发线程 不断重请求队列中获取请求
 * version:1.0
 */
public class RequestDispatcher  extends Thread{
    /**
     * 传递过来的
     */
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public void setmRequestQueue(BlockingQueue<BitmapRequest> mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }
    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                BitmapRequest request = mRequestQueue.take();
                //处理请求对象

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
