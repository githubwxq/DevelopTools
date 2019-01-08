package com.wxq.commonlibrary.util;

import java.util.Collection;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/8/8
 * @description 集合的工具类
 */
public class ListUtils {

    /**
     * 判断集合是否为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }
}
