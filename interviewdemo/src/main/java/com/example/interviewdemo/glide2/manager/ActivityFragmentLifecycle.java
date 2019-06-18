package com.example.interviewdemo.glide2.manager;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 生命周期监听管理器
 */
class ActivityFragmentLifecycle implements Lifecycle {

    //
    private final Set<LifecycleListener> lifecycleListeners =
            Collections.newSetFromMap(new WeakHashMap<LifecycleListener, Boolean>());
    //已启动
    private boolean isStarted;
    //已销毁
    private boolean isDestroyed;

    @Override
    public void addListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);
        if (isDestroyed) {
            listener.onDestroy();
        } else if (isStarted) {
            listener.onStart();
        } else {
            listener.onStop();
        }
    }

    @Override
    public void removeListener(LifecycleListener listener) {
        lifecycleListeners.remove(listener);
    }

    void onStart() {
        isStarted = true;
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onStart();
        }
    }

    void onStop() {
        isStarted = false;
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onStop();
        }
    }

    void onDestroy() {
        isDestroyed = true;
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onDestroy();
        }
    }
}
