package com.wxq.commonlibrary.util;

import android.os.CountDownTimer;

/**
 * Created by 王晓清.
 * data 2018/8/13.
 * describe .倒计时工具类多种倒计时整理
 */

public class CountDownTimerUtils  {
    private int totalTIme;
    private int period;
    private   CountDownStrategy countDownStrategy;

    public static void countDown(int totalTime, int period, CountDownListener listener){
        new CountDownTimer(totalTime*1000, period*1000) {
            @Override
            public void onTick(long currentProcess) {
                listener.currentProcess((int) currentProcess);
            }
            @Override
            public void onFinish() {
                listener.countDownfinish();
            }
        }.start();

    }

    /**
     * 使用Countdown类实现倒计时
     */
     class CountDownTimerHelp implements CountDownStrategy {

        private int totalTIme;
        private int period;
        private CountDownTimer countDownTimer;
        private CountDownListener listener;

        @Override
        public void init(int totalTime, int period, CountDownListener listener) {
            this.totalTIme = totalTime;
            this.period = period;
            this.listener = listener;
        }
        @Override
        public void start(String time) {
            if (countDownTimer!=null) {
                countDownTimer.cancel();
                countDownTimer=null;
            }
             countDownTimer = new CountDownTimer(totalTIme*1000, period*1000) {
                @Override
                public void onTick(long currentProcess) {
                    listener.currentProcess((int) currentProcess);
                }
                @Override
                public void onFinish() {
                    listener.countDownfinish();
                }
            };
            countDownTimer.start();
        }

        @Override
        public void cancel() {
            countDownTimer.cancel();
        }
    }



    /**
     * 使用handler类实现倒计时
     */






    //用于使用策略模式
    public interface CountDownStrategy {
        void init(int totalTime, int period,CountDownListener listener);
        void start(String time);
        void cancel();
    }
    //用于回调外部类实现这个接口作为将结果回调给外部类
    public interface CountDownListener {
        void countDownfinish();
        void currentProcess(int time);
    }

}
