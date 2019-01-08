package com.juziwl.uilibrary.topview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;

/**
 * Created by 王晓清.
 * data 2018/9/2.
 * describe .
 */

public class TopBarHeard extends LinearLayout {

    public RelativeLayout header_container;

    public ImageView iv_header_layoutleft, iv_header_layout_right;

    public TextView tv_header_layout_title, tv_header_layout_right;

    public TopBarHeard(Context context) {
        super(context);
        initView(context);
    }

    public TopBarHeard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TopBarHeard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化相关控件
     *
     * @param context
     */
    private void initView(Context context) {
        //加载布局
        View view = View.inflate(context, R.layout.header_layout
                , this);
        header_container = (RelativeLayout) view.findViewById(R.id.header_container);
        tv_header_layout_title = (TextView) view.findViewById(R.id.tv_header_layout_title);
        tv_header_layout_right = (TextView) view.findViewById(R.id.tv_header_layout_right);
        iv_header_layoutleft = (ImageView) view.findViewById(R.id.iv_header_layoutleft);
        iv_header_layout_right = (ImageView) view.findViewById(R.id.iv_header_layout_right);
    }

    public TopBarHeard setTitle(String title, float size, int colorId) {
        tv_header_layout_title.setTextColor(getContext().getResources().getColor(colorId));
        setTitle(title, size);
        return this;
    }

    public TopBarHeard setTitle(String title, float size) {
        tv_header_layout_title.setTextSize(size);
        setTitle(title);
        return this;
    }

    public TopBarHeard setTitle(String title) {
        tv_header_layout_title.setText(title);
        return this;
    }

    public TopBarHeard setRightText(String title, float size, int colorId) {
        tv_header_layout_title.setTextColor(getContext().getResources().getColor(colorId));
        setRightText(title, size);
        return this;
    }

    public TopBarHeard setRightText(String title, float size) {
        tv_header_layout_title.setTextSize(size);
        setRightText(title);
        return this;
    }

    public TopBarHeard setRightText(String title) {
        tv_header_layout_title.setText(title);
        return this;
    }


    public TopBarHeard setLeftAndRightListener(OnClickListener left, OnClickListener right) {
        if (left == null) {
            iv_header_layoutleft.setVisibility(GONE);
        }else {
            iv_header_layoutleft.setVisibility(VISIBLE);
        }
        if (right == null) {
            iv_header_layout_right.setVisibility(GONE);
        }else {
            iv_header_layout_right.setVisibility(VISIBLE);
        }
        tv_header_layout_right.setOnClickListener(right);
        iv_header_layoutleft.setOnClickListener(left);
        return this;
    }

    public TopBarHeard setLeftListener(OnClickListener left) {
        iv_header_layoutleft.setOnClickListener(left);
        return this;
    }

    public TopBarHeard setRightListener(OnClickListener right) {
        iv_header_layout_right.setOnClickListener(right);
        tv_header_layout_right.setOnClickListener(right);
        return this;
    }


}
