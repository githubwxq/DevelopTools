package com.example.interviewdemo.glide2.load;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.interviewdemo.glide2.cache.recycle.Resource;
import com.example.interviewdemo.glide2.cache.Key;
import com.example.interviewdemo.glide2.request.ResourceCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class EngineJob implements DecodeJob.Callback {

    private static final String TAG = "EngineJob";


    private static final Handler MAIN_THREAD_HANDLER =
            new Handler(Looper.getMainLooper(), new MainThreadCallback());

    private static final int MSG_COMPLETE = 1;
    private static final int MSG_EXCEPTION = 2;
    private static final int MSG_CANCELLED = 3;
    private Resource resource;


    /**
     * 任务工作回调
     */
    public interface EngineJobListener {

        void onEngineJobComplete(EngineJob engineJob, Key key, Resource resource);

        void onEngineJobCancelled(EngineJob engineJob, Key key);
    }

    private EngineKey key;
    private final List<ResourceCallback> cbs = new ArrayList<>();
    private final ThreadPoolExecutor threadPool;
    private final EngineJobListener listener;
    private boolean isCancelled;
    private DecodeJob decodeJob;


    public EngineJob(ThreadPoolExecutor threadPool, EngineKey key, EngineJobListener listener) {
        this.key = key;
        this.threadPool = threadPool;
        this.listener = listener;
    }

    public void addCallback(ResourceCallback cb) {
        Log.e(TAG, "设置加载状态监听");
        cbs.add(cb);
    }

    public void removeCallback(ResourceCallback cb) {
        Log.e(TAG, "移除加载状态监听");
        cbs.remove(cb);
        //这一个请求取消了，可能还有其他地方的请求
        //只有回调为空 才表示请求需要取消
        if (cbs.isEmpty()) {
            cancel();
        }
    }

    void cancel() {
        isCancelled = true;
        decodeJob.cancel();
        listener.onEngineJobCancelled(this, key);
    }

    public void start(DecodeJob decodeJob) {
        Log.e(TAG, "开始加载工作");
        this.decodeJob = decodeJob;
        threadPool.execute(decodeJob);
    }


    @Override
    public void onResourceReady(Resource resource) {
        this.resource = resource;
        MAIN_THREAD_HANDLER.obtainMessage(MSG_COMPLETE, this).sendToTarget();
    }

    @Override
    public void onLoadFailed(Throwable e) {
        MAIN_THREAD_HANDLER.obtainMessage(MSG_EXCEPTION, this).sendToTarget();
    }

    private static class MainThreadCallback implements Handler.Callback {


        @Override
        public boolean handleMessage(Message message) {
            EngineJob job = (EngineJob) message.obj;
            switch (message.what) {
                //加载成功
                case MSG_COMPLETE:
                    job.handleResultOnMainThread();
                    break;
                    //失败
                case MSG_EXCEPTION:
                    job.handleExceptionOnMainThread();
                    break;
                    //取消
                case MSG_CANCELLED:
                    job.handleCancelledOnMainThread();
                    break;
                default:
                    throw new IllegalStateException("Unrecognized message: " + message.what);
            }
            return true;
        }
    }

    private void handleCancelledOnMainThread() {
        listener.onEngineJobCancelled(this, key);
        release();
    }

    /**
     * 图片加载成功
     */
    private void handleResultOnMainThread() {
        if (isCancelled) {
            resource.recycle();
            release();
            return;
        }
        //将引用计数+1
        resource.acquire();
        //1、回调给Engine 操作缓存
        listener.onEngineJobComplete(this, key, resource);
        //2、cbs里面是ResourceCallback集合，这里其实就是一堆的Request
        for (int i = 0, size = cbs.size(); i < size; i++) {
            ResourceCallback cb = cbs.get(i);
            //引用计数 +1
            resource.acquire();
            cb.onResourceReady(resource);
        }
        //将引用计数-1
        resource.release();
        release();
    }

    private void handleExceptionOnMainThread() {
        if (isCancelled) {
            release();
            return;
        }
        listener.onEngineJobComplete(this, key, null);
        for (ResourceCallback cb : cbs) {
            cb.onResourceReady(null);
        }
    }

    private void release() {
        cbs.clear();
        key = null;
        resource = null;
        isCancelled = false;
        decodeJob = null;
    }

}
