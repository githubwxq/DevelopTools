package com.example.uitestdemo.fragment.components.flowview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.floatview.easyfloatview.FloatingViewListener;
import com.juziwl.uilibrary.floatview.easyfloatview.FloatingViewManager;
import com.wxq.commonlibrary.util.DensityUtil;

/**
 * 发布悬浮窗管理
 */
public class FloatViewInstance implements FloatingViewListener {

    private FloatingViewManager mFloatingViewManager;

    private View floatView;



    private  Context mcontext;


    private static FloatViewInstance instance;

    private FloatViewInstance(Context context) {
        this.mcontext=context;
    }

    public static FloatViewInstance getInstance(Context context) {
        FloatViewInstance inst = instance;
        if (inst == null) {
            synchronized (FloatViewInstance.class) {
                inst = instance;
                if (inst == null) {
                    inst = new FloatViewInstance(context);
                    instance = inst;
                }
            }
        }
        return inst;
    }





    boolean isViewInit=false;


    public void showFloatingView() {
        if (!isViewInit) {
            this.mFloatingViewManager = new FloatingViewManager(mcontext, this);
            FloatingViewManager.Configs configs = new FloatingViewManager.Configs();
            configs.floatingViewX = 0;
            configs.floatingViewY = DensityUtil.dip2px(mcontext,160);
            configs.overMargin = - DensityUtil.dip2px(mcontext,10);
            configs.floatingViewWidth =  DensityUtil.dip2px(mcontext,60);
            configs.floatingViewHeight =  DensityUtil.dip2px(mcontext,60);
            floatView = LayoutInflater.from(mcontext).inflate(R.layout.float_view_item, null, false);

            this.mFloatingViewManager.addFloatingView(floatView, configs);
            isViewInit=true;
        }
    }




    /**
     * 销毁悬浮窗
     */
    public void destroyFloatingView() {
        if (this.mFloatingViewManager != null) {
            if (isViewInit) {
                this.mFloatingViewManager.removeAllFloatingView();
                this.mFloatingViewManager = null;
                isViewInit=false;
            }
        }
    }



    @Override
    public void onFinishFloatingView() {
     // 悬浮窗销毁处理
    }


//    public void hideFloatContentView() {
//        if (isViewInit) {
//            floatView.setVisibility(View.GONE);
//        }
//
//    }
//    //显示悬浮窗内容
//    public void showFloatContentView() {
//        if (isViewInit) {
//            floatView.setVisibility(View.VISIBLE);
//        }
//    }

}
