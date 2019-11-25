package com.juziwl.uilibrary.emoji;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by haojx on 2018/5/16.
 *
 * @author Neil
 * @version exue 3.0
 * @date 2018/5/16 10:19
 * @description
 */

public class CollapsibleView extends LinearLayout implements View.OnClickListener{
    /**
     * 不显示
     */
    private static final int COLLAPSIBLE_STATE_NONE = 0;
    /**
     * 显示收起
     */
    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;
    /**
     * 显示全文
     */
    private static final int COLLAPSIBLE_STATE_SPREAD = 2;
    /**
     * 定义的高度
     */
    private int height = 210;
    private int llHeight = 0;

    private int mState = COLLAPSIBLE_STATE_NONE;
    private static final String COLLAPSIBLE_STATE_SHRINKUP_TEXT = "收起";
    private static final String COLLAPSIBLE_STATE_SPREAD_TEXT = "全文";
    private TextView textView;


    private final Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            ViewGroup.LayoutParams lp = getLayoutParams();
            llHeight = getHeight();
            if (getHeight() <= height) {
                //高度不足时候不处理
                mState = COLLAPSIBLE_STATE_NONE;
                textView.setVisibility(GONE);
            } else {
                switch (mState) {
                    //显示全文
                    case COLLAPSIBLE_STATE_SPREAD:
                        lp.height = height;
                        setLayoutParams(lp);
                        textView.setVisibility(VISIBLE);
                        textView.setText(COLLAPSIBLE_STATE_SPREAD_TEXT);
                        textView.setTextSize(30);
                        break;
                    //收起状态
                    case COLLAPSIBLE_STATE_SHRINKUP:
                        lp.height = llHeight;
                        setLayoutParams(lp);
                        textView.setText(COLLAPSIBLE_STATE_SHRINKUP_TEXT);
                        textView.setTextSize(30);
                        break;
                    default:
                        textView.setVisibility(GONE);
                        break;
                }
            }
        }
    };


    public CollapsibleView(Context context) {
        this(context, null);
    }

    public CollapsibleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
//        View v = inflate(getContext(), R.layout.collapsible, this);
//        textView = (TextView) v.findViewById(R.id.tv_show);
        textView = new TextView(getContext());
        addView(textView);
        textView.setOnClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        handler.sendMessage(Message.obtain());
    }

    @Override
    public void onClick(View v) {
        if (mState == COLLAPSIBLE_STATE_SPREAD) {
            // 如果是全文状态，就改成收起状态
            mState = COLLAPSIBLE_STATE_SHRINKUP;
            requestLayout();
        }
        else if (mState == COLLAPSIBLE_STATE_SHRINKUP) {
            // 如果是收起状态，就改成全文状态
            mState = COLLAPSIBLE_STATE_SPREAD;
            requestLayout();
        }
    }
}
