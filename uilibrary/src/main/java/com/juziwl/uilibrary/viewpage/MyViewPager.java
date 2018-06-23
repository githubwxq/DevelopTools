package com.juziwl.uilibrary.viewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class MyViewPager extends HackyViewPager {
	  private boolean scrollble=true;

	  public MyViewPager(Context context) {
	    super(context);
	  }

	  public MyViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }


	  @Override
	  public boolean onTouchEvent(MotionEvent ev) {
	    if (!scrollble) {
	      return true;
	    }

	    return super.onTouchEvent(ev);
	  }


	  public boolean isScrollble() {
	    return scrollble;
	  }

	  public void setScrollble(boolean scrollble) {
	    this.scrollble = scrollble;
	  }

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		 if(getCurrentItem()!=0){
			 getParent().requestDisallowInterceptTouchEvent(true);
		 }else{
			 getParent().requestDisallowInterceptTouchEvent(false);
		 }

		return super.dispatchTouchEvent(ev);
	}
}
