package com.juziwl.uilibrary.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author null
 * @modify Neil
 */
public class ResizeLayout extends LinearLayout {
	private OnResizeListener mListener;
	public ResizeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		if (mListener != null) {
            mListener.OnResize(w, h, oldw, oldh); 
        } 
	}
	
	public interface OnResizeListener {
		/**
		 * 重新测量大小
		 * @param w
		 * @param h
		 * @param oldw
		 * @param oldh
		 */
        void OnResize(int w, int h, int oldw, int oldh); 
    } 
     
    public void setOnResizeListener(OnResizeListener l) { 
        mListener = l; 
    }
	

}
