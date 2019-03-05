package com.juziwl.uilibrary.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/05
 * desc:
 * version:1.0
 */
public class XfermodeView extends CustomView {
    Paint mDstPaint,mSrcPaint;
    Bitmap mSrcBitmap,mDstBitmap;
    Canvas mSrcCanvas,mDstCanvas;

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        super.init();




    }
}
