package com.wxq.commonlibrary.toolsqlite.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gongsiwei on 2018/5/4.
 */

public class Tool {
    /**
     * 判断字符串是否为空
     *
     * @param s 目标字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    /**
     * 判断数组是否为空
     *
     * @param arr 数组
     * @return true：为空;false：不为空。
     */
    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 判断int数组是否为空
     *
     * @param arr
     * @return
     */
    public static boolean isEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 判断集合是否为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 获取当前时间
     *
     * @param pattern 格式
     * @return 格式化的当前时间
     */
    public static String getNowDate(String pattern) {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String s = simpleDateFormat.format(d);
        return s;
    }
}
