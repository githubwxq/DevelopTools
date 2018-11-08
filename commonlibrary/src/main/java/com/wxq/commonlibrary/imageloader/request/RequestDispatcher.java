package com.wxq.commonlibrary.imageloader.request;

import android.util.Log;

import com.wxq.commonlibrary.imageloader.loader.Loader;
import com.wxq.commonlibrary.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:转发器请求转发线程 不断重请求队列中获取请求
 * version:1.0
 */
public class RequestDispatcher extends Thread {
    /**
     * 传递过来的
     */
    private BlockingQueue<BitmapRequest> mRequestQueue;
    /**
     * 标记当前线程
     */
    public String tag;

    public RequestDispatcher(BlockingQueue<BitmapRequest> mRequestQueue, String tag) {
        this.mRequestQueue = mRequestQueue;
        this.tag = tag;
    }


    public void setmRequestQueue(BlockingQueue<BitmapRequest> mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //阻塞函数
                BitmapRequest request = mRequestQueue.take();
                //处理请求对象处理加载对象
                Log.e("wxq", "当前线程" + tag + "处理request" + request.getSerialNo());
                //处理请求对象  判断是本地 还是其他的
                String schema = parseSchema(request.getImageUrl());
                Loader loader = LoaderManager.getmInstance().getLoader(schema);
                loader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseSchema(String imageUrl) {
        if (imageUrl.contains("://")) {
            //返回请求头
            return imageUrl.split("://")[0];
        } else {
            Log.e("wxq", "不支持此类型");
        }
        return null;
    }
}
