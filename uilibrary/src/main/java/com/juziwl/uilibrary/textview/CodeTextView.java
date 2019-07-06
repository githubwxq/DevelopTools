package com.juziwl.uilibrary.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.juziwl.uilibrary.R;

/**
 * 获取验证码控件
 */
public class CodeTextView extends android.support.v7.widget.AppCompatTextView {
    private int total, interval;
    private String psText;
    public CodeTextView(Context context) {
        super(context);
    }
    public CodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义属性，并赋值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CodeTextView);
        total = typedArray.getInteger(R.styleable.CodeTextView_tb_totalTime, 60000);
        interval = typedArray.getInteger(R.styleable.CodeTextView_tb_timeInterval, 1000);
        psText = typedArray.getString(R.styleable.CodeTextView_tb_psText);
        setBackgroundResource(R.drawable.code_text_view_select); //设置默认样式
        typedArray.recycle();
    }

    //执行
    public void start() {
        TimeCount time = new TimeCount(total, interval);
        time.start();
    }

    public  class TimeCount extends CountDownTimer{
        private long countDownInterval;
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setEnabled(false);
            setText(millisUntilFinished / countDownInterval + "秒");
        }

        @Override
        public void onFinish() {
            setText(psText);
            setEnabled(true);
        }
    }
}
