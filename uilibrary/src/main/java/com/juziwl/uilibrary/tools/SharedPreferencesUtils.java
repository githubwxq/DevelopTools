package com.juziwl.uilibrary.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 说明：SharedPreferences 工具类
 * 作者　　: 杨阳
 * 创建时间: 2017/1/14 16:07
 */

public class SharedPreferencesUtils {
    //记录第一次登录
    public final static String FIRST_LOGIN = "FIRST_LOGIN";

    /**
     * 设置String 键值对内容
     *
     * @param context    上下文对象
     * @param SPFileName sp文件名称
     * @param key        键
     * @param value      值
     */
    public static void setStringContent(Context context, String SPFileName, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);

        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取String 键值对内容
     *
     * @param context    上下文对象
     * @param SPFileName sp文件名称
     * @param key        键
     * @return 返回值
     */
    public static String getStringContent(Context context, String SPFileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);
        try {
            return sp.getString(key, "");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 设置Boolean 键值对内容
     *
     * @param context    上下文对象
     * @param SPFileName sp文件名称
     * @param key        键
     * @param value      值
     */
    public static void setBooleanContent(Context context, String SPFileName, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);

        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取 Boolean 键值对内容
     *
     * @param context    上下文对象
     * @param SPFileName sp文件名称
     * @param key        键
     * @param value      默认值
     * @return
     */
    public static Boolean getBooleanContent(Context context, String SPFileName, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    //设置 int 键值对内容
    public static void setIntContent(Context context, String SPFileName, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);

        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //获取 int 键值对内容
    public static int getIntContent(Context context, String SPFileName, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    //设置 long 键值对内容
    public static void setLongContent(Context context, String SPFileName, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);

        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    //获取 long 键值对内容
    public static long getLongContent(Context context, String SPFileName, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(SPFileName, context.MODE_PRIVATE);
        return sp.getLong(key, value);
    }


}
