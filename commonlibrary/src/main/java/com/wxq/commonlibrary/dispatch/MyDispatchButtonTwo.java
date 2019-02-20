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
public class MyDispatchButtonTwo extends android.support.v7.widget.AppCompatButton {
    private static String TAG = "MyDispatchButtonTwo";

    public MyDispatchButtonTwo(Context context) {
        super(context);
    }

    public MyDispatchButtonTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDispatchButtonTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG,"dispatchTouchEvent====MyButton=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG,"dispatchTouchEvent====MyButton=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG,"dispatchTouchEvent====MyButton=====ACTION_UP");
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG,"onTouchEvent====MyButton=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG,"onTouchEvent====MyButton=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG,"onTouchEvent====MyButton=====ACTION_UP");
        }
        return super.onTouchEvent(event);
    }



}


//结论
////
//        那么我们继续回归dispatchTouchEvent中不是ViewGroup的情形：
//        接下来，系统会自动判断我们是否实现了onTouchListener 这里就开始有分支了
//        --1>. 当我们实现了onTouchListener，那么下一步我们的事件叫交给了onTouchListener.onTouch来处理，这里就又开始了分支
//        （1）如果我们在onTouch中返回了true，那么就表明我们的onTouchListener 已经消化掉了本次的事件，本次事件完结。这就是为什么我们在onTouch中返回去就永运不会执行onClick，onLongClick了
//        （2）如果我们在onTouch中返回了false，那么很明显了我们的事件就会被onTouchEvent处理
//        --2>. 同理，当我们没有实现了onTouchListener，很明显了我们的事件就会被onTouchEvent处理。
//        殊途同归，最终如果我们的事件没有被干掉，最终都交给了onTouchEvent。那么接下来我们继续来看onTouchEvent，那么我们的onTouchEvent又是用来干什么的呢（这里既然已经有onTouchListener了，他们似乎一模一样啊）？
//        其实不然，说白了我们的！！！onTouchEvent！！！最终会用来分发onClick和onLongClick事件
