package com.example.im_huanxing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

public class ImActivity extends BaseActivity {
    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, ImActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_im;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
