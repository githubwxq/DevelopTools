package com.example.kotlintestdemo;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;

public class ViewUtil {
    public static void addExtraAnimClickListener(View view, final ValueAnimator animator, final View.OnClickListener listener) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        animator.start();
                        break;

                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        animator.reverse();
                        break;
                }


                return false;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }
}
