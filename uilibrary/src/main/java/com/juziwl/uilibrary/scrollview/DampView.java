package com.juziwl.uilibrary.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * @author null
 * @modify Neil
 * 阻尼效果的scrollview
 */

public class DampView extends ScrollView {
    private static final int LEN = 0xc8;
    private static final int DURATION = 500;
    private static final int MAX_DY = 200;
    private Scroller mScroller;
    private TouchTool tool;
    private int left, top;
    private float startX, startY, currentX, currentY;
    private int imageViewH;
    private int rootW, rootH;
    private ImageView imageView;
    private boolean scrollerType;
    private int scrollY = 0;
    private int defaultHeight = 0;

    public DampView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context);
    }

    public DampView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DampView(Context context) {
        this(context, null, 0);

    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (defaultHeight == 0) {
            defaultHeight = imageView.getHeight();
        }
        int action = event.getAction();
        if (!mScroller.isFinished()) {
            return super.dispatchTouchEvent(event);
        }
        currentX = event.getX();
        currentY = event.getY();
        imageView.getTop();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                left = imageView.getLeft();
                top = imageView.getBottom();
                rootW = getWidth();
                rootH = getHeight();
                imageViewH = imageView.getHeight();
                startX = currentX;
                startY = currentY;
                tool = new TouchTool(imageView.getLeft(), imageView.getBottom(),
                        imageView.getLeft(), imageView.getBottom() + LEN);
                break;
            case MotionEvent.ACTION_MOVE:
                if (scrollY > 0) {
                    return super.dispatchTouchEvent(event);
                }
                if (imageView.isShown() && imageView.getTop() >= 0) {
                    if (tool != null) {
                        int t = tool.getScrollY(currentY - startY);
                        if (t >= top && t <= imageView.getBottom() + LEN) {
                            android.view.ViewGroup.LayoutParams params = imageView
                                    .getLayoutParams();
                            params.height = t;
                            imageView.setLayoutParams(params);
                        }
                    }
                    scrollerType = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (scrollY > 0) {
                    return super.dispatchTouchEvent(event);
                }
                scrollerType = true;
                mScroller.startScroll(imageView.getLeft(), imageView.getBottom(),
                        0 - imageView.getLeft(),
                        imageViewH - imageView.getBottom(), DURATION);
                invalidate();
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scrollY <= 0) {
            if (mScroller.computeScrollOffset()) {
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();
                imageView.layout(0, 0, x + imageView.getWidth(), y);
                invalidate();
                if (!mScroller.isFinished() && scrollerType && y > MAX_DY) {
                    android.view.ViewGroup.LayoutParams params = imageView
                            .getLayoutParams();
                    params.height = y;
                    imageView.setLayoutParams(params);
                }
            }
        }
    }

//    public void finishScroll() {
//        scrollerType = true;
//        mScroller.startScroll(imageView.getLeft(), imageView.getBottom(),
//                0 - imageView.getLeft(),
//                imageViewH - imageView.getBottom(), DURATION);
//        invalidate();
//    }

    public class TouchTool {

        private int startX, startY;

        public TouchTool(int startX, int startY, int endX, int endY) {
            super();
            this.startX = startX;
            this.startY = startY;
        }

        public int getScrollX(float dx) {
            int xx = (int) (startX + dx / 2.5F);
            return xx;
        }

        public int getScrollY(float dy) {
            int yy = (int) (startY + dy / 2.5F);
            return yy;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        scrollY = t;
        if (listener != null) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public interface onScrollListener {
        /**
         * 滑动改变
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public onScrollListener listener;

    public void setOnScrollListener(onScrollListener listener) {
        this.listener = listener;
    }

    public boolean isPullState() {
        return imageView.getHeight() > defaultHeight;
    }
}
