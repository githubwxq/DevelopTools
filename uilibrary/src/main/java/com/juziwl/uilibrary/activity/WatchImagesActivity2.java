package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.otherview.SlideCloseLayout;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 高仿今日头条查看页面
 */
public class WatchImagesActivity2 extends BaseActivity {

   SlideCloseLayout slideCloseLayout;
   ViewPager viewPager;

    List<String> imgs;
   int firstPosition;

    public static void navToActivity(Context context, String imgs, int firstPosition) {
        Bundle bundle = new Bundle();
        bundle.putString("imgs", imgs);
        bundle.putInt("firstPosition", firstPosition);
        Intent intent = new Intent(context, WatchImagesActivity2.class);
        context.startActivity(intent);
    }

    public static void navToActivity(Context context, ArrayList<String> imgs, int firstPosition) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("imgs", imgs);
        bundle.putInt("firstPosition", firstPosition);
        Intent intent = new Intent(context, WatchImagesActivity2.class);
        context.startActivity(intent);
    }




    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, WatchImagesActivity2.class);
        activity.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_watch_images2;
    }

    @Override
    protected void initViews() {
        slideCloseLayout=findViewById(R.id.scl);
        //给控件设置需要渐变的背景。如果没有设置这个，则背景不会变化
        slideCloseLayout.setGradualBackground(getWindow().getDecorView().getBackground());
        //设置监听，滑动一定距离后让Activity结束
        slideCloseLayout.setLayoutScrollListener(new SlideCloseLayout.LayoutScrollListener() {
            @Override
            public void onLayoutClosed() {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in_150,R.anim.fade_out_150);
            }
        });
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        viewPager=findViewById(R.id.scl_pager);
        if (getIntent().getStringArrayListExtra("imgs")!=null) {
            imgs=getIntent().getStringArrayListExtra("imgs");
        }
        if (getIntent().getStringExtra("imgs")!=null) {
            imgs= Arrays.asList(getIntent().getStringExtra("imgs").split(";"));
        }
        firstPosition=getIntent().getIntExtra("firstPosition",0);
        //设置activity的背景为黑色


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            slideCloseLayout.layoutExitAnim(1000, false);
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
