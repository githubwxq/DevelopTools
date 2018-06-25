package com.wxq.commonlibrary.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author ztn
 * @version V_5.0.0
 * @date 2016年2月18日
 * @description 应用程序Activity的管理类
 */
public class AppManager {
    private Stack<Activity> mActivityStack = new Stack<>();
    private static AppManager mAppManager;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement();
        }
        return null;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        if (mActivityStack != null) {
            Activity activity = mActivityStack.lastElement();
            killActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {
            if (mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        if (cls != null && mActivityStack != null) {
            for (int i = mActivityStack.size() - 1; i >= 0; i--) {
                if (mActivityStack.get(i).getClass().equals(cls)) {
                    killActivity(mActivityStack.get(i));
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        if (mActivityStack != null) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (null != mActivityStack.get(i)) {
                    mActivityStack.get(i).finish();
                }
            }
            mActivityStack.clear();
        }
    }

    public void killActivity(String className) {
        if (mActivityStack != null) {
            Iterator<Activity> iterator = mActivityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (className != null && className.equals(activity.getClass().getName())) {
                    activity.finish();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityMgr != null) {
                activityMgr.killBackgroundProcesses(context.getPackageName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stack<Activity> getmActivityStack() {
        return mActivityStack;
    }

    /**
     * 只留一个activity
     *
     * @param classNames 类名，不含包名
     */
    public void finishActivitysWithoutSome(String... classNames) {
        if (classNames == null || classNames.length == 0) {
            return;
        }
        if (mActivityStack == null) {
            return;
        }
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!containString(classNames, activity.getClass().getSimpleName())) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    public static boolean containString(String[] array, String value) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            if (array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
}
