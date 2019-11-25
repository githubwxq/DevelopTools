package com.example.skinlibrary;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 皮肤生命周期监控
 */
class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {


    HashMap<Activity, SkinLayoutFactory> mLayoutFactoryMap = new HashMap<>();
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        /**
         *  更新状态栏
         */
        SkinThemeUtils.updateStatusBar(activity);
        /**
         * 更新字体
         */
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);

        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        //获得Activity的布局加载器
        try {
            //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
            //如设置过抛出一次
            //设置 mFactorySet 标签为false
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);  // 设置false 然后用自己的layoutinflater
        } catch (Exception e) {
            e.printStackTrace();
        }

        SkinLayoutFactory skinLayoutFactory = new SkinLayoutFactory(activity, typeface);

        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutFactory);

        //注册观察者  外城重新设置了皮肤你这边接受到update更新  最终skinLayoutFactory更新数据就会更新说有的页面了应为每个页面的
        SkinManager.getInstance().addObserver(skinLayoutFactory);

        mLayoutFactoryMap.put(activity, skinLayoutFactory);

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //删除观察者
        SkinLayoutFactory skinLayoutFactory = mLayoutFactoryMap.remove(activity);
        SkinManager.getInstance().deleteObserver(skinLayoutFactory);
    }

    /**
     * 更新某个activity
     * @param activity
     */
    public void updateSkin(Activity activity) {
        SkinLayoutFactory skinLayoutFactory = mLayoutFactoryMap.get(activity);
        skinLayoutFactory.update(null, null);

    }
}
