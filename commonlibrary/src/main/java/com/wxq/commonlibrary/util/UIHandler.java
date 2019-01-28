package com.wxq.commonlibrary.util;

import android.os.Handler;
import android.os.Looper;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/04/20
 * @description
 */
public class UIHandler {
    private Handler handler = null;

    private UIHandler() {
        handler = new Handler(Looper.getMainLooper());
    }

    private static class SingletonHolder {
        private static UIHandler instance = new UIHandler();
    }

    public static UIHandler getInstance() {
        return SingletonHolder.instance;
    }

    public void post(Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        }
    }

    public void postDelayed(Runnable runnable, long delay) {
        if (handler != null) {
            handler.postDelayed(runnable, delay);
        }
    }

    public void removeCallbacks(Runnable runnable) {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
