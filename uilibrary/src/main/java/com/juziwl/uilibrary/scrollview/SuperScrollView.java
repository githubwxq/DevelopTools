package com.juziwl.uilibrary.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import static java.lang.Boolean.FALSE;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/17
 * desc:scrowview
 * version:1.0
 */
public class SuperScrollView extends ScrollView {


    private void init(Context context) {
       //隐藏bar
      setVerticalScrollBarEnabled(false);

    }

    public SuperScrollView(Context context) {
        super(context);
        init(context);
    }


    public SuperScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SuperScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


//
//    滑动方向不同之以ScrollView与ViewPager为例的外部解决法
//    从 父View 着手，重写 onInterceptTouchEvent 方法，在 父View 需要拦截的时候拦截，不要的时候返回false，

    private float mDownPosX = 0;
    private float mDownPosY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }




















}


//、、常用属性配置
//  android:fillViewport="true"
//  android:scrollbars="none"


//注意如果底部布局不想被软键盘弹出 记得最后的布局设置在它上一个控件的下面这样就不会被顶上去了


//首先我想到了Activity的windowSoftInputMode属性，这个属性能影响两件事情：
//        1、当有焦点产生时，软键盘是隐藏还是显示
//        2、是否改变活动主窗口大小以便腾出空间展示软键盘
//        它有以下值可以设置：
//        1、stateUnspecified：软键盘的状态并没有指定，系统将选择一个合适的状态或依赖于主题的设置
//        2、stateUnchanged：当这个activity出现时，软键盘将一直保持在上一个activity里的状态，无论是隐藏还是显示
//        3、stateHidden：用户选择activity时，软键盘总是被隐藏
//        4、stateAlwaysHidden：当该Activity主窗口获取焦点时，软键盘也总是被隐藏的
//        5、stateVisible：软键盘通常是可见的
//        6、stateAlwaysVisible：用户选择activity时，软键盘总是显示的状态
//        7、adjustUnspecified：默认设置，通常由系统自行决定是隐藏还是显示
//        8、adjustResize：该Activity总是调整屏幕的大小以便留出软键盘的空间
//        9、adjustPan：当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖，并且用户总能看到输入内容的部分
//        可以设置一个或多个，多个之间用|分开。这些值中符合我们要求的是adjustResize和adjustPan。先看adjustPan，它会将当前获取了焦点的EditText之上的布局整体上移，以此来达到EditText不被软键盘覆盖的目的。但如果我只想让EditText和与EditText有关的一些控件上移，而它们之上的控件保持不变呢?OK，这时候我们就需要用到adjustResize，但是光用它还是不够的，还需要我们的布局配合。
//        ---------------------
//        作者：康小白Code
//        来源：CSDN
//        原文：https://blog.csdn.net/k_bb_666/article/details/78544738?utm_source=copy
//        版权声明：本文为博主原创文章，转载请附上博文链接！