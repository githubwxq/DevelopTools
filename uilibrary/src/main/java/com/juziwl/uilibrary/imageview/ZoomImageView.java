package com.juziwl.uilibrary.imageview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/25
 * desc:
 * version:1.0
 */
public class ZoomImageView extends androidx.appcompat.widget.AppCompatImageView implements
        ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener {

    private boolean mOnce = false;

    /*
     初始化缩放的植
     */
    private float mInitScale;


    /*
   双击居中放大的值
   */
    private float mMidScale;

    /*
    放大的最大值
    */
    private float mMaxScale;
    private Matrix mScaleMatrix;

    private ScaleGestureDetector mScaleGesture;

    private GestureDetector mGestureDetector;


    /*
    记录上一次多点触控的数量手纸变化 中心点会有变化
    */
    private int mLastPointerCounter;
    /*
        记录上一次多点触控的数量手纸变化 中心点会有变化
        */
    private float mLastX;
    private float mLastY;
    private int mTouchSlop;
    private boolean isCanDrag;


    public ZoomImageView(Context context) {
        this(context, null, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init   图片过大缩小 图片过小放大
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGesture = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //早一个已经写好的类重新腹泻他
        mGestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            //
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                if (getScale()<mMidScale) {
                    mScaleMatrix.postScale(mMidScale/getScale(),mMidScale/getScale(),x,y);
                    setImageMatrix(mScaleMatrix);
                }else{
                    //缩放对应比列
                    mScaleMatrix.postScale(mInitScale/getScale(),mInitScale/getScale(),x,y);
                    setImageMatrix(mScaleMatrix);
                }
                return true;
            }
        });
    }


    private class AutoScaleRunable implements Runnable{

        private float x;
        private float y;
        private float mTargetScale;
        private final float BIGGET=1.07f;
        private final float SMALL=0.93f;

        private float tmpScale;

        public AutoScaleRunable(float x, float y, float mTargetScale) {
            this.x = x;
            this.y = y;
            this.mTargetScale = mTargetScale;
        }

        @Override
        public void run() {


        }


    }



    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }


    /*
    获取图片大小
     */
    @Override
    public void onGlobalLayout() {
        //布局完成调用
        if (!mOnce) {
            //获取控件的宽和高
            int width = getWidth();
            int height = getHeight();
            //获取图片的宽和高
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();
            float scale = 1.0f;

            //图片宽度大于控件宽度 ，但是高度小于控件高度 将其缩小
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }
            //图片高度大于控件宽度 ，但是高度小于控件宽度 将其缩小
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            //图片宽高都大于  取最小职
            if (dw > width && dh > height) {
                scale = (float) Math.min(width * 1.0 / dw, height * 1.0f / dh);
            }

            if (dw < width && dh < height) {
                scale = (float) Math.min(width * 1.0 / dw, height * 1.0f / dh);
            }
               /*
               得到初始化的值
                */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;
                /*
               将图片移动到控件的中心  确定移动的x y轴的移动距离
                */
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;
            // xscale xskew xtrans
            // yskew  yscale ytrans
            //  0      0       0
            mScaleMatrix.postScale(mInitScale, mInitScale);
            mScaleMatrix.postTranslate(dx, dy);
            setImageMatrix(mScaleMatrix);
            mOnce = true;
        }
    }

    /**
     * 获取当前图片的说放置
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    // 多点触控  判断在不在硕放区间
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        Log.e("wxq", scaleFactor + "缩放比例");
        if (getDrawable() == null) return true;
        //缩放范围控制
        if (scale < mMaxScale && scaleFactor > 1.0f || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }
        }
        mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        //防止放大缩小 引起边界问题
        checkBorderAndCenterWhenScale();
        setImageMatrix(mScaleMatrix);
        return true;
    }

    /*
      获取图片上下左右宽高 个点坐标
     */
    private RectF getMatrixRectf() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF); //得到放大缩小后的坐标
        }

        return rectF;
    }


    /**
     * pandu
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectf();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();

        //缩放时进行辩解监测 防止出现百变
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }

        //
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }

        //如果宽度或者高度小于控件的宽高  让其居中
        if (rect.width() < width) {
            deltaX = width / 2f - rect.right + rect.width() / 2f;
        }
        if (rect.height() < height) {
            deltaY = height / 2f - rect.bottom + rect.height() / 2f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);


    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {

        return true; // 必须是true
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mGestureDetector.onTouchEvent(event))return true;



        mScaleGesture.onTouchEvent(event);

        float x = 0;
        float y = 0;
        //拿到众仙殿
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;

        //中心点
        if (mLastPointerCounter != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCounter = pointerCount;
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {
                    RectF rect = getMatrixRectf();
                    if (getDrawable() != null) {
//                        宽度小于控件宽度不允许横向移动
                        if (rect.width() < getWidth()) {
                            dx = 0;
                        }

//                        if (rect.height() < getHeight()) {
//                            dy = 0;
//                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                       mLastPointerCounter=0;
                break;

        }


        return true; //事件拦截
    }

    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }
}
