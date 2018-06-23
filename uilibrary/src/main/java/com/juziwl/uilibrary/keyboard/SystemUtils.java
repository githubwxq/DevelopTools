package com.juziwl.uilibrary.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.ScreenUtils;


/**
 * Created by xy on 15/12/23.
 * @author null
 * @modify Neil
 */
public class SystemUtils {

    /**
     * 可见屏幕高度
     **/
    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    /**
     * 关闭键盘
     **/
    public static void hideSoftInput(View paramEditText, Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    /**
     * 获取actiobar高度
     **/
//    public static int getActionBarHeight(Activity paramActivity) {
//        if (true) {
//            return SystemUtils.dip2px(56);
//        }
//        int[] attrs = new int[]{android.R.attr.actionBarSize};
//        TypedArray ta = paramActivity.obtainStyledAttributes(attrs);
//        return ta.getDimensionPixelSize(0, SystemUtils.dip2px(56));
//    }

    /**
     * dp转px
     **/
    public static int dip2px(int dipValue, Context context) {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    /**
     * 键盘是否在显示
     **/
    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = ScreenUtils.getScreenHeight() - BarUtils.getStatusBarHeight()
                - getAppHeight(paramActivity);
        return height != 0;
    }

    /**
     * 显示键盘   必须得在edittext可见下才能操作
     **/
    public static void showKeyBoard(final View paramEditText, Context context) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramEditText, 0);
            }
        });
    }

    /**
     * 获取键盘高度，不可以加adjustResize,实验未通过
     */
    public interface IKeyBoardVisibleListener {
        /**
         * 软键盘是否可见
         * @param visible
         * @param windowBottom
         */
        void onSoftKeyBoardVisible(boolean visible, int windowBottom);
    }

    static boolean isVisiableForLast = false;

    public static void addOnSoftKeyBoardVisibleListener(Activity activity, final IKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                //获得键盘高度
                int keyboardHeight = hight - displayHight;
                boolean visible = (double) displayHight / hight < 0.8;
                if (visible != isVisiableForLast) {
                    listener.onSoftKeyBoardVisible(visible, keyboardHeight);
                }
                isVisiableForLast = visible;
            }
        });
    }
}
