package com.example.design_pattern.single;

import java.util.HashMap;
import java.util.Map;

//单例管理类
public class SingletonManager {
    private static Map<String, Object> objMap = new HashMap<String, Object>();

    public static void registerService(String key, Object instance) {
        if (!objMap.containsKey(key)) {
            objMap.put(key, instance);//添加单例
        }
    }

    public static Object getService(String key) {
        return objMap.get(key);//获取单例
    }
}