package com.example.trackpoint;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @author : martin
 * @date : 2018/9/6
 */
public class AopUtil {

    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static AopUtil instance;

    /**
     * 是否登录
     */
    public boolean isLogin = false;

    private AopUtil(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        instance = new AopUtil(context);
    }

    public static AopUtil getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }
}
