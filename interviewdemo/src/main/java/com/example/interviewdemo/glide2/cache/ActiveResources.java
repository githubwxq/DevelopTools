package com.example.interviewdemo.glide2.cache;

import com.example.interviewdemo.glide2.cache.recycle.Resource;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/5/4.
 * 正在使用的图片资源
 */
public class ActiveResources {

    private ReferenceQueue<Resource> queue;
    private final Resource.ResourceListener resourceListener;
    private Map<Key, ResourceWeakReference> activeResources = new HashMap<>();
    private Thread cleanReferenceQueueThread;
    private boolean isShutdown;

    public ActiveResources(Resource.ResourceListener resourceListener) {
        this.resourceListener = resourceListener;
    }

    /**
     * 加入活动缓存
     *
     * @param key
     * @param resource
     */
    public void activate(Key key, Resource resource) {
        resource.setResourceListener(key, resourceListener);
        activeResources.put(key, new ResourceWeakReference(key, resource, getReferenceQueue()));
    }

    /**
     * 移除活动缓存
     */
    public Resource deactivate(Key key) {
        ResourceWeakReference reference = activeResources.remove(key);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }


    /**
     * 获得对应value
     * @param key
     * @return
     */
    public Resource get(Key key) {
        ResourceWeakReference reference = activeResources.get(key);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

    /**
     * 引用队列，通知我们弱引用被回收了
     * 让我们得到通知的作用
     *
     * @return
     */
    private ReferenceQueue<Resource> getReferenceQueue() {
        if (null == queue) {
            queue = new ReferenceQueue<>();
            cleanReferenceQueueThread = new Thread() {
                @Override
                public void run() {
                    while (!isShutdown) {
                        try {
                            //被回收掉的引用
                            ResourceWeakReference ref = (ResourceWeakReference) queue.remove();
                            activeResources.remove(ref.key);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            };
            cleanReferenceQueueThread.start();
        }
        return queue;
    }


    public void shutdown() {
        isShutdown = true;
        if (cleanReferenceQueueThread != null) {
            cleanReferenceQueueThread.interrupt();
            try {
                //5s  必须结束掉线程
                cleanReferenceQueueThread.join(TimeUnit.SECONDS.toMillis(5));
                if (cleanReferenceQueueThread.isAlive()) {
                    throw new RuntimeException("Failed to join in time");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static final class ResourceWeakReference extends WeakReference<Resource> {

        final Key key;

        public ResourceWeakReference(Key key, Resource referent,
                                     ReferenceQueue<? super Resource> queue) {
            super(referent, queue);
            this.key = key;
        }
    }


}
