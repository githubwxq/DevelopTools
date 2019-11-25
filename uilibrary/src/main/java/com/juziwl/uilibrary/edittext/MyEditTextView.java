package com.juziwl.uilibrary.edittext;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author null
 * @modify Neil
 */
public class MyEditTextView extends AppCompatEditText {
	  private boolean scrollble=true;

	  public MyEditTextView(Context context) {
	    super(context);
	  }

	  public MyEditTextView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }


	  @Override
	  public boolean onTouchEvent(MotionEvent ev) {
//	    if (!scrollble) {
//	      return true;
//	    }

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
         //请求父控件不拦截事件
		 getParent().requestDisallowInterceptTouchEvent(true);

		return super.dispatchTouchEvent(ev);
	}
}
