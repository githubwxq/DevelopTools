/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright 2014 Manabu Shimobe
 * Copyright 2016 Chen Sir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.juziwl.uilibrary.otherview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;

/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright 2014 Manabu Shimobe
 * Copyright 2016 Chen Sir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class ExpandableLinearLayout extends LinearLayout implements View.OnClickListener {

    /* The default number of lines */
    private static final int MAX_COLLAPSED_LINES = 8;

    /* The default animation duration */
    private static final int DEFAULT_ANIM_DURATION = 300;


    /* The default content text size*/
    private static final float DEFAULT_CONTENT_TEXT_SIZE = 16;
    private static final float DEFAULT_CONTENT_TEXT_LINE_SPACING_MULTIPLIER = 1.0f;

    private static final int STATE_TV_GRAVITY_LEFT = 0;
    private static final int STATE_TV_GRAVITY_CENTER = 1;
    private static final int STATE_TV_GRAVITY_RIGHT = 2;

//    protected TextView mTv;


    protected TextView mStateTv; // TextView to expand/collapse
    protected View view_zhe; // TextView to expand/collapse

    public boolean ismRelayout() {
        return mRelayout;
    }

    public void setmRelayout(boolean mRelayout) {
        this.mRelayout = mRelayout;
    }

    private boolean mRelayout;

    private boolean mCollapsed = true; // Show short version as default.

    private int mCollapsedHeight;

    private int mOldHeight;

//    private int mMaxCollapsedLines;

    public int getmMaxContentHeight() {
        return mMaxContentHeight;
    }

    public void setmMaxContentHeight(int mMaxContentHeight) {
        this.mMaxContentHeight = mMaxContentHeight;
    }

    //默任显示的高度 dp
    private int mMaxContentHeight = 150;

    private int mMarginBetweenTxtAndBottom;

    private Drawable mExpandDrawable;

    private Drawable mCollapseDrawable;

    private int mStateTvGravity;

    private String mCollapsedString;

    private String mExpandString;

    private boolean mAnimating;

    private OnExpandStateChangeListener mListener;

    private ViewGroup expand_content;

    public ExpandableLinearLayout(Context context) {
        this(context, null);
    }

    public ExpandableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ExpandableLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.HORIZONTAL == orientation) {
            throw new IllegalArgumentException("ExpandableTextView only supports Vertical Orientation.");
        }
        super.setOrientation(orientation);
    }

    @Override
    public void onClick(View view) {
        if (mStateTv.getVisibility() != View.VISIBLE) {
            return;
        }
        mCollapsed = !mCollapsed;
        mStateTv.setText(mCollapsed ? mExpandString : mCollapsedString);
        mAnimating = true;
        //当前关闭
        if (mCollapsed) {
            expand_content.getLayoutParams().height = dip2px(mMaxContentHeight);
            expand_content.setTag(R.id.expand_ll, true);
            expand_content.requestLayout();
            if (mListener != null) {
                mListener.isShowZheZhao(true);
            }
        } else {
            expand_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            expand_content.setTag(R.id.expand_ll, false);
            expand_content.requestLayout();
            if (mListener != null) {
                mListener.isShowZheZhao(false);
            }
        }
        if (mListener != null) {
            mListener.onExpandStateChanged(mCollapsed);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;
        mStateTv.setVisibility(View.GONE);
        mListener.isShowZheZhao(false);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (expand_content.getMeasuredHeight() <= dip2px(mMaxContentHeight)) {
            Object isSetting = expand_content.getTag(R.id.expand_ll);
            if (isSetting != null && (boolean) isSetting) {
                mStateTv.setVisibility(View.VISIBLE);
                mListener.isShowZheZhao(true);
            }
            return;
        }
        //    需要处理显示状态

        mOldHeight = expand_content.getMeasuredHeight();
        mStateTv.setVisibility(View.VISIBLE);
        mListener.isShowZheZhao(true);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mCollapsed) {
            expand_content.getLayoutParams().height = dip2px(mMaxContentHeight);
            expand_content.setTag(R.id.expand_ll, true);
            mListener.isShowZheZhao(true);
        } else {
            mListener.isShowZheZhao(false);
            expand_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            expand_content.setTag(R.id.expand_ll, false);
            getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }

    public void setOnExpandStateChangeListener(@Nullable OnExpandStateChangeListener listener) {
        mListener = listener;
    }


    public void setFlag(boolean collapsed) {
        mCollapsed = collapsed;
        mStateTv.setText(mCollapsed ? mExpandString : mCollapsedString);
        mRelayout = true;
        expand_content.setTag(R.id.expand_ll, false);
        expand_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    @Override
    public void requestLayout() {
        mRelayout = true;
        super.requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLinearLayout);

        if (mExpandString == null) {
            mExpandString = this.getContext().getString(R.string.expand_string);
        }
        if (mCollapsedString == null) {
            mCollapsedString = this.getContext().getString(R.string.collapsed_string);
        }
        typedArray.recycle();
    }

    private void findViews() {
        mStateTv = (TextView) findViewById(R.id.expand_collapse);
        expand_content = (ViewGroup) findViewById(R.id.expand_ll);
        mStateTv.setText(mCollapsed ? mExpandString : mCollapsedString);
        mStateTv.setOnClickListener(this);


    }

    private static boolean isPostLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        Resources resources = context.getResources();
        if (isPostLolipop()) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }


    public interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         * <p>
         * - TextView being expanded/collapsed
         *
         * @param isExpanded - true if the TextView has been expanded
         */
        void onExpandStateChanged(boolean isExpanded);


        void isShowZheZhao(boolean isShow);


    }

    /**
     * dp转px
     */
    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}


//使用方法
//布局中
//<me.chensir.expandabletextview.ExpandableLinearLayout
//        android:id="@+id/tv"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        >
//
//
//
//
//<LinearLayout
//                android:visibility="visible"
//                        android:id="@+id/expand_ll"
//                        android:orientation="vertical"
//                        android:layout_width="wrap_content"
//                        android:layout_height="wrap_content">
//<TextView
//                    android:text="11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
//                            android:id="@+id/expandable_text"
//                            android:layout_width="match_parent"
//                            android:layout_height="wrap_content" />
//<ImageView
//                    android:background="#fff0"
//                            android:layout_width="match_parent"
//                            android:layout_height="100dp" />
//</LinearLayout>
//<TextView
//                android:id="@+id/expand_collapse"
//                        android:text="展开"
//                        android:layout_width="wrap_content"
//                        android:layout_height="wrap_content" />
//</me.chensir.expandabletextview.ExpandableLinearLayout>
// 列表中
//    viewHolder.expandableLinearLayout.setFlag( true);

