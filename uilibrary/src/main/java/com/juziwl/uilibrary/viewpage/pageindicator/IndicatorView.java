package com.juziwl.uilibrary.viewpage.pageindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.utils.ConvertUtils;
import com.juziwl.uilibrary.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * 指示器
 */
public class IndicatorView extends LinearLayout {

    private Context mContext=null;
    private ArrayList<View> dotViews =new ArrayList<>();
    private float size = 6;
    private int marginSize=15;
    private int pointSize ;
    private int marginLeft;
    private LayoutParams layoutParams=null;
    private int normalId=R.drawable.white_point ;
    private int selectId=R.drawable.green_point;
    
    
    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private void init() {
        pointSize= ConvertUtils.dp2px(size,mContext);
        marginLeft=ConvertUtils.dp2px(marginSize,mContext);
    }

    public void initIndicator(int count){
        removeAllViews();
        for (int i = 0 ; i<count ; i++){
            View view = new View(mContext);
            layoutParams = new LayoutParams(pointSize,pointSize);
            if(i!=0)
                 layoutParams.leftMargin = marginLeft;
            view.setLayoutParams(layoutParams);
            if (i == 0){
                view.setBackgroundResource(selectId);
            }else{
                view.setBackgroundResource(normalId);
            }
            dotViews.add(view);
            addView(view);
        }
    }

    public void indicatorView(int start,int next){
        if(start < 0 || next < 0 || next == start){
            start = next = 0;
        }
        final View ViewStrat =  dotViews.get(start);
        final View ViewNext =  dotViews.get(next);
        ViewNext.setBackgroundResource(normalId);
        ViewStrat.setBackgroundResource(selectId);
    }

    public void setCurrentIndicate(int currentPosition){
        for (View dotView : dotViews) {
            dotView.setBackgroundResource(normalId);
        }
        if (dotViews.get(currentPosition)!=null) {
            dotViews.get(currentPosition).setBackgroundResource(selectId);
        }
    }
}