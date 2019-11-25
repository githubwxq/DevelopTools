package com.wxq.commonlibrary.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/20
 * desc: 测试事件分发button
 * version:1.0
 */
public class MyDispatchButton extends androidx.appcompat.widget.AppCompatButton {
    private static String TAG = "MyDispatchButton";

    public MyDispatchButton(Context context) {
        super(context);
    }

    public MyDispatchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDispatchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true); //外层viewpage不要拦截事件 这样我滑动 外层不要拦截并处理事件
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG, "dispatchTouchEvent====MyButton=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG, "dispatchTouchEvent====MyButton=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG, "dispatchTouchEvent====MyButton=====ACTION_UP");
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG, "onTouchEvent====MyButton=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG, "onTouchEvent====MyButton=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG, "onTouchEvent====MyButton=====ACTION_UP");
        }

        Log.e(TAG, "button是否消费事件" + super.onTouchEvent(event));

        return super.onTouchEvent(event);
    }


}


//view 事件分发部分结
//        那么我们继续回归dispatchTouchEvent中动不是ViewGroup的情形：
////        接下来，系统会自判断我们是否实现了onTouchListener 这里就开始有分支了
//        --1>. 当我们实现了onTouchListener，那么下一步我们的事件叫交给了onTouchListener.onTouch来处理，这里就又开始了分支
//        （1）如果我们在onTouch中返回了true，那么就表明我们的onTouchListener 已经消化掉了本次的事件，本次事件完结。这就是为什么我们在onTouch中返回去就永运不会执行onClick，onLongClick了
//        （2）如果我们在onTouch中返回了false，那么很明显了我们的事件就会被onTouchEvent处理
//        --2>. 同理，当我们没有实现了onTouchListener，很明显了我们的事件就会被onTouchEvent处理。
//        殊途同归，最终如果我们的事件没有被干掉，最终都交给了onTouchEvent。那么接下来我们继续来看onTouchEvent，那么我们的onTouchEvent又是用来干什么的呢（这里既然已经有onTouchListener了，他们似乎一模一样啊）？
//        其实不然，说白了我们的！！！onTouchEvent！！！最终会用来分发onClick和onLongClick事件


//注意  1  button默认ontouchevent  返回true
//      2  原来在onTouchEvent的Motion.ACTION_DOWN分支如果返回的是false，
//         那么与actiondown 相关的事件不会被分发 比如move或者up  onTouch事件将不会往下面传递下去（不清楚是move何up了当然不分发），
//         如果返回true，那么onTouch事件将会传递下去，从而可以响应Motion.ACTION_UP分支。

//注意  父控件一旦拦截事件 子控件没事件 父控件只能接受到actiondown 事件 并且默认ontouchevent 不会处理这个事件

//    requestDisallowInterceptTouchEvent 是ViewGroup类中的一个公用方法，参数是一个boolean值
//        android系统中，一次点击事件是从父view传递到子view中，每一层的view可以决定是否拦截并处理点击事件或者传递到下一层，
//        如果子view不处理点击事件，则该事件会传递会父view，由父view去决定是否处理该点击事件。
//        在子view可以通过设置此方法去告诉父view不要拦截并处理点击事件，父view应该接受这个请求直到此次点击事件结束。
//
//        实际的应用中，可以在子view的ontouch事件中注入父ViewGroup的实例，
//        并调用requestDisallowInterceptTouchEvent去阻止父view拦截点击事件。

//public boolean onTouch(View v, MotionEvent event) {
//        2      ViewGroup viewGroup = (ViewGroup) v.getParent();
//        3      switch (event.getAction()) {
//        4      case MotionEvent.ACTION_MOVE:
//        5          viewGroup.requestDisallowInterceptTouchEvent(true);
//        6          break;
//        7      case MotionEvent.ACTION_UP:
//        8      case MotionEvent.ACTION_CANCEL:
//        9          viewGroup .requestDisallowInterceptTouchEvent(false);
//        10          break;
//        11      }
//        12 }



