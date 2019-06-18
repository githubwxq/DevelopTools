package com.example.interviewdemo.glide2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class Target {


    private static int maxDisplayLength = -1;
    private SizeReadyCallback cb;
    private LayoutListener layoutListener;
    private ImageView view;

    public void cancel() {
        ViewTreeObserver observer = view.getViewTreeObserver();
        if (observer.isAlive()) {
            observer.removeOnPreDrawListener(layoutListener);
        }
        if (null != layoutListener)
            layoutListener.release();
        layoutListener = null;
        cb = null;
    }

    public void onLoadFailed(Drawable error) {
        view.setImageDrawable(error);
    }

    public void onLoadStarted(Drawable placeholderDrawable) {
        view.setImageDrawable(placeholderDrawable);
    }

    public void onResourceReady(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }


    private static final class LayoutListener
            implements ViewTreeObserver.OnPreDrawListener {
        private Target target;

        LayoutListener(Target target) {
            this.target = target;
        }

        @Override
        public boolean onPreDraw() {
            if (null != target) {
                target.checkCurrentDimens();
            }
            return true;
        }

        public void release() {
            target = null;
        }
    }

    public interface SizeReadyCallback {
        void onSizeReady(int width, int height);
    }


    public Target(ImageView imageView) {
        this.view = imageView;
    }

    public void getSize(SizeReadyCallback cb) {
        //获得宽
        int currentWidth = getTargetWidth();
        //获得高
        int currentHeight = getTargetHeight();
        //
        if (currentHeight > 0 && currentWidth > 0) {
            cb.onSizeReady(currentWidth, currentHeight);
            return;
        }

        this.cb = cb;
        if (layoutListener == null) {
            //视图绘制前回调 回调中能获得宽与高
            ViewTreeObserver observer = view.getViewTreeObserver();
            layoutListener = new LayoutListener(this);
            observer.addOnPreDrawListener(layoutListener);
        }
    }

    private int getTargetHeight() {
        int verticalPadding = view.getPaddingTop() + view.getPaddingBottom();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int layoutParamSize = layoutParams != null ? layoutParams.height : 0;
        return getTargetDimen(view.getHeight(), layoutParamSize, verticalPadding);
    }

    private int getTargetWidth() {
        //获得view的padding view的宽-padding才是内容的宽
        int horizontalPadding = view.getPaddingLeft() + view.getPaddingRight();
        //获得view的布局属性 1、给定的大小 2、wrap_content
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int layoutParamSize = layoutParams != null ? layoutParams.width : 0;
        return getTargetDimen(view.getWidth(), layoutParamSize, horizontalPadding);
    }

    /**
     *
     * @param viewSize view.getXX
     * @param paramSize  LayoutParams
     * @param paddingSize padding
     * @return
     */
    private int getTargetDimen(int viewSize, int paramSize, int paddingSize) {
        //1、如果是固定大小
        int adjustedParamSize = paramSize - paddingSize;
        if (adjustedParamSize > 0) {
            return adjustedParamSize;
        }

        //2、如果能够由 view.getWidth() 获得大小
        int adjustedViewSize = viewSize - paddingSize;
        if (adjustedViewSize > 0) {
            return adjustedViewSize;
        }

        //3、如果布局属性设置的是包裹内容并且我们不能接到回调了
        // 回调 是什么？ addOnPreDrawListener
        //表示不会回调 onPreDraw
        if (!view.isLayoutRequested() && paramSize == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return getMaxDisplayLength(view.getContext());
        }
        return 0;
    }

    /**
     * 获得一个最大允许的view大小
     * @param context
     * @return
     */
    private static int getMaxDisplayLength(Context context) {
        if (maxDisplayLength == -1) {
            WindowManager windowManager =
                    (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point displayDimensions = new Point();
            //获得屏幕大小
            display.getSize(displayDimensions);
            // 最大的屏幕大小
            maxDisplayLength = Math.max(displayDimensions.x, displayDimensions.y);
        }
        return maxDisplayLength;
    }

    void checkCurrentDimens() {
        if (null == cb) {
            return;
        }
        int currentWidth = getTargetWidth();
        int currentHeight = getTargetHeight();
        if (currentHeight <= 0 && currentWidth <= 0) {
            return;
        }
        cb.onSizeReady(currentWidth, currentHeight);
        cancel();
    }
}
