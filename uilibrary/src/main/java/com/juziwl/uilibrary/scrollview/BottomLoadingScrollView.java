package com.juziwl.uilibrary.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author null
 * @modify Neil
 */
public class BottomLoadingScrollView extends ScrollView {

    private static final int NUMBER_75 = 75;
	private ScrollBottomListener scrollBottomListener;  
	  
    public BottomLoadingScrollView(Context context) {
        this(context,null,0);
    }  
  
    public BottomLoadingScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }  
  
    public BottomLoadingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
  
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){  
        if(t + getHeight() >=  computeVerticalScrollRange()-NUMBER_75){
            //ScrollView滑动到底部了
            if(scrollBottomListener!=null){
                scrollBottomListener.scrollBottom();
            }
        }
    }  
  
    public void setScrollBottomListener(ScrollBottomListener scrollBottomListener){  
        this.scrollBottomListener = scrollBottomListener;  
    }  
  
    public interface ScrollBottomListener{
        /**
         * 底部滚动
         */
        public void scrollBottom();  
    }

}
