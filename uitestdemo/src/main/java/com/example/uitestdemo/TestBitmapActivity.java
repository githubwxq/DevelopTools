package com.example.uitestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.util.ConvertUtils;

public class TestBitmapActivity extends BaseActivity {

    ImageView oldImage;
    ImageView nowImage;

    @Override
    protected void initViews() {
        int px = ConvertUtils.dp2px(100);
        LogUtil.e("100dp对应的宽度"+px);
        Drawable drawable = getResources().getDrawable(R.mipmap.text);
        BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
        final Bitmap bmm = bitmapDrawable.getBitmap();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.text);
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        LogUtil.e("bitmapHeight"+bitmapHeight+"bitmapWidth"+bitmapWidth);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_bitmap;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
