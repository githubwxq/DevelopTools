package com.wxq.commonlibrary.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:阻塞队列
 * version:1.0
 */
public class RequestQueue {

    /**
     * 阻塞级队列 多线程共享
     * 生产效率大于处理效率
     * 优先级比较高的线执行
     */

    //排序关联 实现优先级 需要实现优先级接口然后queue里面自己会处理的
    private BlockingQueue<BitmapRequest> mRequestQueue=new PriorityBlockingQueue<>();


    /**
     * 转发器数量
     */
    private  int threadCount;

    /**
     * 转发器对象
     */
    private  RequestDispatcher[] mDispachers;


    private AtomicInteger i=new AtomicInteger(0);



    /**
     * 添加请求
     */
    public void addRequest(BitmapRequest request){
        //判断请求队列包含请求
        if (!mRequestQueue.contains(request)) {
            //给请求进行编号
            request.setSerialNo(i.incrementAndGet());
            mRequestQueue.add(request);
        }else {
            Log.e("wxq","请求编号以及存在"+request.getSerialNo());
        }
    }





    /**
     * 开始请求
     */
    public void start(BitmapRequest request){
        //判断请求队列包含请求
        if (!mRequestQueue.contains(request)) {
            //给请求进行编号
            request.setSerialNo(i.incrementAndGet());
            mRequestQueue.add(request);
        }else {
            Log.e("wxq","请求编号以及存在"+request.getSerialNo());
        }
    }





    /**
     * 暂停请求
     */
    public void stop(BitmapRequest request){
        //判断请求队列包含请求
        if (!mRequestQueue.contains(request)) {
            //给请求进行编号
            request.setSerialNo(i.incrementAndGet());
            mRequestQueue.add(request);
        }else {
            Log.e("wxq","请求编号以及存在"+request.getSerialNo());
        }
    }









}
