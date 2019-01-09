package com.wxq.developtools;

import com.wxq.commonlibrary.base.BaseView;
import com.wxq.commonlibrary.base.RxPresenter;

/**
 * Created by wxq on 2018/6/28.
 *
 * //p成拿到view成數據
 */

public class MvpMainPresent extends RxPresenter<MvpMainContract.View> implements MvpMainContract.Presenter {


    public MvpMainPresent(MvpMainContract.View view) {
        super(view);
    }

    @Override
    public void getData(int count) {
       mView.showToast(count*3+"wawawawaa");
       mView.showRx();
    }


    @Override
    public void initEventAndData() {
//      、、倒计时
//        CountDownTimer countDownTimer=new CountDownTimer(10*1000, 2000) {
//            @Override
//            public void onTick(long l) {
//
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };

//        countDownTimer.start();
//        CountDownTimerUtils.countDown(10, 1, new CountDownTimerUtils.CountDownListener() {
//            @Override
//            public void countDownfinish() {
//                mView.showToast("倒计时结束");
//            }
//
//            @Override
//            public void currentProcess(int time) {
//                mView.showToast(time+"");
//            }
//        });
    }




}
