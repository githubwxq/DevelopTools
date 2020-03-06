package com.example.uitestdemo;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.LogUtils;
import com.wxq.commonlibrary.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PingTuWeight extends FrameLayout {

    int width;
    int height;


    List<PingTuMode> list = new ArrayList<>();

    public PingTuWeight(@NonNull Context context) {
        super(context);
        initView(context);

//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int x = (int) motionEvent.getX();
//                int y = (int) motionEvent.getY();
//                LogUtils.e("x"+x);
//                LogUtils.e("y"+y);
//                return false;
//            }
//        });

    }

    private void initView(Context context) {
        width = ScreenUtils.getScreenWidth();
        height = ScreenUtils.getScreenWidth();
        String picPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic1.jpg";
        String picPath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic2.jpg";

        PingTuMode pingTuMode1 = new PingTuMode(picPath1);
        pingTuMode1.left = 0;
        pingTuMode1.top = 0;
        pingTuMode1.right = width;
        pingTuMode1.bottom = height / 3;


        PingTuMode pingTuMode2 = new PingTuMode(picPath2);
        pingTuMode2.left = 0;
        pingTuMode2.top = height / 3;
        pingTuMode2.right = width;
        pingTuMode2.bottom = height;

        list.add(pingTuMode1);
        list.add(pingTuMode2);
//        list.add(new PingTuMode(picPath));
        for (PingTuMode pingTuMode : list) {
            PhotoView photoView = new PhotoView(context);
            photoView.setOnScaleChangeListener(new PhotoViewAttacher.OnScaleChangeListener() {
                @Override
                public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                    LogUtils.e(  "scaleFactor"+scaleFactor+"focusX"+focusX+"focusY"+focusY);
                }
            });

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {

                }
            });
            photoView.setMaxScale(5);
            photoView.setMinimumScale(1);
//            photoView.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
            addView(photoView);
        }
//        invalidate();
        requestLayout();
    }



    public PingTuWeight(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < list.size(); i++) {
            PhotoView childAt = (PhotoView) getChildAt(i);
            PingTuMode pingTuMode = list.get(i);
//            childAt.setBackgroundColor(R.color.yellow_50);
            LoadingImgUtil.loadimg(pingTuMode.picPath, childAt, false);
            childAt.setBackgroundColor(getContext().getResources().getColor(R.color.yellow_300));
            childAt.layout(pingTuMode.left, pingTuMode.top, pingTuMode.right, pingTuMode.bottom);
        }

    }

    int currentDownPosition=-1;
    int currentUpPosition=-1;
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        // 当前按下的图片多指情况下不做判断

       int action= motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                currentDownPosition=-1;
                LogUtils.e(  "down"+"x"+x+"y"+y);
                //  判断当前坐标在哪个区域内
                for (int i = 0; i < list.size(); i++) {
                    PingTuMode mode = list.get(i);
                      if (x>=mode.left&&x<=mode.right&&y>=mode.top&&y<=mode.bottom){
                          currentDownPosition=i;
                      }
                }

                LogUtils.e(  "当前按下的是集合中的第"+currentDownPosition+"的位置");

            break;

            case MotionEvent.ACTION_MOVE:
                //一但是多指情况 最后up的时候不处理 指定currentDownpositon为-1即可
                if (motionEvent.getPointerCount()>1) {
                    currentDownPosition=-1;
                }

                LogUtils.e(  "move"+"x"+x+"y"+y);
                break;
            case MotionEvent.ACTION_UP:
                currentUpPosition=-1;
                LogUtils.e(  "up"+"x"+x+"y"+y);
                LogUtils.e(  "down"+"x"+x+"y"+y);
                //  判断当前坐标在哪个区域内
                for (int i = 0; i < list.size(); i++) {
                    PingTuMode mode = list.get(i);
                    if (x>=mode.left&&x<=mode.right&&y>=mode.top&&y<=mode.bottom){
                        currentUpPosition=i;
                    }
                }

                LogUtils.e(  "当前抬起的是集合中的第"+currentUpPosition+"的位置");

                // 如果都不是-1 并且不相等 图片地址更换其他的相关信息不变
                if (currentDownPosition!=-1&&currentUpPosition!=-1&&currentDownPosition!=currentUpPosition) {
                    String temp= list.get(currentDownPosition).picPath;
                    list.get(currentDownPosition).picPath= list.get(currentUpPosition).picPath;
                    list.get(currentUpPosition).picPath=temp;
                    requestLayout();
                }


                break;
        }


        return super.dispatchTouchEvent(motionEvent);
    }
}
