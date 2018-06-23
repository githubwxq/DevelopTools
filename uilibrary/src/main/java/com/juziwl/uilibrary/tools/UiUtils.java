package com.juziwl.uilibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;


public class UiUtils {
    
    /**
     * 获得一个空间的宽高   防0
     */

    public static void setViewMeasure(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    /**
     * 获得一个空间的宽   防0
     */

    public static void setViewWidth(View view) {
        setViewMeasure(view);
        view.getMeasuredWidth();
    }

    public static void setViewHeight(View view) {
        setViewMeasure(view);
        view.getMeasuredHeight();
    }

    /**
     * 从资源文件获取一个view
     */
    public static View getInflaterView(Context context, int res) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(res, null);
        applyFont(view);
        return view;
    }

    /**
     * 从资源文件获取一个view
     */
    public static View getInflaterView(Context context, int res, ViewGroup parent) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(res, parent);
        applyFont(view);
        return view;
    }

    /**
     * 从资源文件获取一个view
     */
    public static View getInflaterView(Context context, int res, ViewGroup parent, boolean attachToRoot) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(res, parent, attachToRoot);
        applyFont(view);
        return view;
    }


    /*
     * 设置ImageView渲染（Tint）
     */
    public static void setImageViewTint(final ImageView view, final int color) {
        view.setColorFilter(view.getResources().getColor(color));
    }


    /*
     * 设置字体
     */
    public static void applyFont(final View root) {
//        try {
//            if (root instanceof ViewGroup) {
//                ViewGroup viewGroup = (ViewGroup) root;
//                for (int i = 0; i < viewGroup.getChildCount(); i++)
//                    applyFont(viewGroup.getChildAt(i));
//            } else if (root instanceof TextView)
//
////                ((TextView) root).setTypeface(MyApplication.myApp.typeface);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static View getRootView(Activity context) {
        return context.getWindow().getDecorView()
                .findViewById(android.R.id.content);
    }

    public static View getDecorView(Activity context) {
        return context.getWindow().getDecorView();
    }



    public static boolean isOpenInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        return isOpen;
    }

}