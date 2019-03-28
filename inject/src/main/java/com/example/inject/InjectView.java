package com.example.inject;

import android.app.Activity;
import android.view.View;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/28
 * desc:
 * version:1.0
 */
public class InjectView {
    public static void bind(Activity actvity) {
        //编译的时候存在的 所以只能通过反射 源代码里面是没有这个编译出现的类的
        String clasName = actvity.getClass().getName();

        Class<?> viewBidClass = null;
        try {
            viewBidClass = Class.forName(clasName + "$$ViewBinder");
            ViewBinder viewBinder = (ViewBinder) viewBidClass.newInstance();
            viewBinder.bind(actvity); //binder 方法实现注入

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
