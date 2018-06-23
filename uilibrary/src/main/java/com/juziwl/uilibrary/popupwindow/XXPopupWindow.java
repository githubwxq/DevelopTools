package com.juziwl.uilibrary.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;

import com.wxq.commonlibrary.util.ScreenUtils;


/**
 * @author null
 * @modify Neil
 */
public abstract class XXPopupWindow {
    private PopupWindow mPopupWindow;
    private Window window;
    private PopupWindowViewHolder popupWindowVh;
    private View mRootView;
    private boolean mIsFocusable = true;
    private boolean mIsOutside = true;
    private Context mcontext;

    public XXPopupWindow(Context context, int layoutId) {
        mcontext = context;
        popupWindowVh = PopupWindowViewHolder.get(context, layoutId);
        mRootView = popupWindowVh.getConvertView();
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setContentView(mRootView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//      mPopupWindow.setAnimationStyle(mAnimationStyle);
        mPopupWindow.setFocusable(mIsFocusable);
        mPopupWindow.setOutsideTouchable(mIsOutside);
        convert(popupWindowVh);
        mPopupWindow.update(); //更新属性
    }


    /**
     * 把弹出框view holder传出去
     *
     * @param holder
     */
    public abstract void convert(PopupWindowViewHolder holder);

    public XXPopupWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
            mPopupWindow.update(); //更新属性
        }

        return this;
    }

    public XXPopupWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
            mPopupWindow.update(); //更新属性
        }

        return this;
    }


    /**
     * 关闭popWindow
     */
    public void dissmiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public int getWidth() {

        if (mPopupWindow != null) {

            return mPopupWindow.getWidth();
        }
        return 0;
    }

    /**
     * 全屏显示
     */
    public XXPopupWindow fullScreen() {
        mPopupWindow.setWidth(ScreenUtils.getScreenWidth());
        mPopupWindow.update();
        return this;
    }

    public XXPopupWindow setOnDissmissListener(PopupWindow.OnDismissListener dismissListener) {
        mPopupWindow.setOnDismissListener(dismissListener);
        return this;
    }

    /**
     * @param width  自定义的宽度
     * @param height 自定义的高度
     * @return
     */
    public XXPopupWindow setWidthAndHeight(int width, int height) {
        mPopupWindow.update(width, height);
        return this;
    }


    /**
     * 设置显示在v上方(以v的左边距为开始位置)
     *
     * @param v
     */
    public XXPopupWindow showUp(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - mPopupWindow.getWidth() / 2, location[1] - mPopupWindow.getHeight());
        mPopupWindow.update();
        return this;
    }


}
