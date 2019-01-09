package com.wxq.commonlibrary.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;


/**
 * ManifestParser
 */
public final class ManifestParser {
    private static final String MODULE_VALUE = "IModuleConfig";

    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public List<IModuleConfig> parse() {
        List<IModuleConfig> modules = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        //根据名字获取对应的值  判断值是不是对应的
                        modules.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
//            throw new RuntimeException("解析Application失败", e);
              return modules;
        }

        return modules;
    }

    private static IModuleConfig parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return (IModuleConfig) module;
    }
}