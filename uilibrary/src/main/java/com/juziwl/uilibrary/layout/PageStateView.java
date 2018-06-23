package com.juziwl.uilibrary.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;

/**
 * @author 王晓清
 * @modify Neil
 * @version V_1.0.0
 * @date 2017/6/29
 * @description 页面状态封装
 *
 */

public class PageStateView extends FrameLayout {
    /**
     * 内容为空
     */
    public static final int NOMESSAGE = 1;
    /**
     * BIUBIU...  正在请求地球网络
     */
    public static final int NONETWORK = 2;
    /**
     * 还没有订阅内容  空
     */
    public static final int NOSUBSCRIBE = 3;
    /**
     * 你还没有添加通讯录 空
     */
    public static final int NOTONGXUN = 4;
    /**
     * 轻触屏幕，重新加载  一定要轻轻的...
     */
    public static final int ERRORPAGE = 5;
    /**
     * 空空如也 （作业）
     */
    public static final int NOHOMEWORK = 6;
    /**
     * 暂无通知
     */
    public static final int NONOTIFICATION = 7;

    /**
     * 还没有食谱噢
     */
    public static final int NORECIPES = 8;

    /**
     * 暂无内容
     */
    public static final int NULL = 9;

    public static final String STR_DEFALUT = "";
    public static final String STR_BIUBIU = "BIUBIU... ";
    public static final String STR_PLEASE_SOFT = "一定要轻轻的...";
    public static final String STR_FOR_NOMESSAGE = "内容为空";
    public static final String STR_FOR_NONETWORK = "正在请求地球网络";
    public static final String STR_FOR_NOSUBSCRIBE = "还没有订阅内容";
    public static final String STR_FOR_NOTONGXUN = "你还没有添加通讯录";
    public static final String STR_FOR_ERRORPAGE = "轻触屏幕，重新加载";
    public static final String STR_FOR_NOHOMEWORK = "空空如也";
    public static final String STR_FOR_NONOTIFICATION = "暂无通知";
    public static final String STR_FOR_NORECIPES = "还没有食谱噢";
    public static final String STR_FOR_NULL = "暂无内容";

    private RelativeLayout rl_page;
    private ImageView iv_pic;
    private TextView tv_detail, tv_detail2;
    public PageStateView(Context context) {
        super(context);
        initView(context);
    }


    public PageStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PageStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        //加载布局
        View view = View.inflate(context, R.layout.common_page_state_view, this);
        rl_page = (RelativeLayout) view.findViewById(R.id.rl_page);
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        tv_detail = (TextView) view.findViewById(R.id.tv_detail);
        tv_detail2 = (TextView) view.findViewById(R.id.tv_detail2);
    }


    //show展示

    public void showStatePage(int state) {
        rl_page.setVisibility(VISIBLE);
        if (state == NOMESSAGE) {
            iv_pic.setImageResource(R.mipmap.common_state_1);
            tv_detail.setText(STR_FOR_NOMESSAGE);
            tv_detail2.setText(STR_DEFALUT);
        } else if (state == NONETWORK) {
            iv_pic.setImageResource(R.mipmap.common_state_2);
            tv_detail.setText(STR_BIUBIU);
            tv_detail2.setText(STR_FOR_NONETWORK);
        } else if (state == NOSUBSCRIBE) {
            iv_pic.setImageResource(R.mipmap.common_state_3);
            tv_detail.setText(STR_FOR_NOSUBSCRIBE);
            tv_detail2.setText(STR_DEFALUT);
        } else if (state == NOTONGXUN) {
            iv_pic.setImageResource(R.mipmap.common_state_4);
            tv_detail.setText(STR_FOR_NOTONGXUN);
            tv_detail2.setText(STR_DEFALUT);
        } else if (state == ERRORPAGE) {
            iv_pic.setImageResource(R.mipmap.common_state_5);
            tv_detail.setText(STR_FOR_ERRORPAGE);
            tv_detail2.setText(STR_PLEASE_SOFT);
        } else if (state == NOHOMEWORK) {
            iv_pic.setImageResource(R.mipmap.common_state_5);
            tv_detail.setText(STR_FOR_NOHOMEWORK);
            tv_detail2.setText(STR_DEFALUT);
        } else if (state == NONOTIFICATION) {
            iv_pic.setImageResource(R.mipmap.common_state_5);
            tv_detail.setText(STR_FOR_NONOTIFICATION);
            tv_detail2.setText(STR_DEFALUT);
        } else if(state == NORECIPES){
            iv_pic.setImageResource(R.mipmap.icon_no_food);
            tv_detail.setText(STR_FOR_NORECIPES);
            tv_detail2.setText(STR_DEFALUT);
        }else if(state == NULL){
            iv_pic.setImageResource(R.mipmap.contentnull);
            tv_detail.setText(STR_FOR_NULL);
            tv_detail2.setText(STR_DEFALUT);
        }
    }


    public void hidStatePage() {
        rl_page.setVisibility(GONE);
    }




}
