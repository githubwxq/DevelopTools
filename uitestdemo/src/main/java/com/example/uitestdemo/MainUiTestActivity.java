package com.example.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wxq.mvplibrary.router.RouterContent;

@Route(path = RouterContent.AROUTER_MAIN)
public class MainUiTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui_test);
    }
}
