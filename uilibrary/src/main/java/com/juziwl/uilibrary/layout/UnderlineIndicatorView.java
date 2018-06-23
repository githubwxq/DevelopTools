package com.juziwl.uilibrary.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.juziwl.uilibrary.R;

/**
 * @author null
 * @modify Neil
 */
public class UnderlineIndicatorView extends LinearLayout {

    private int mCurrentPosition;

    public UnderlineIndicatorView(Context context) {
        this(context, null);
    }

    public UnderlineIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.common_UnderlineIndicatorView);
        int count = a.getInt(R.styleable.common_UnderlineIndicatorView_common_count, 4);
        a.recycle();

        for (int i = 0; i < count; i++) {
            View view = new View(context);
            LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.color.common_transparent);
            addView(view);
        }
    }

    public void setCurrentItemWithoutAnim(int position) {
        final View oldChlid = getChildAt(mCurrentPosition);
        final View newChild = getChildAt(position);

        oldChlid.setBackgroundResource(R.color.common_transparent);
        newChild.setBackgroundResource(R.color.common_account_orange);

        mCurrentPosition = position;
        invalidate();
    }

    public void setCurrentItem(int position) {
        final View oldChlid = getChildAt(mCurrentPosition);
        final View newChild = getChildAt(position);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, position - mCurrentPosition,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(200);

        translateAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                oldChlid.setBackgroundResource(R.color.common_transparent);
                newChild.setBackgroundResource(R.color.common_account_orange);
            }
        });

        oldChlid.setAnimation(translateAnimation);
        mCurrentPosition = position;
        new Handler().postDelayed(() -> {
            //execute the task
            invalidate();
        }, 100);

     //   invalidate();
    }

}