package com.example.aroutertestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.nettestdemo.R;
import com.wxq.mvplibrary.router.RouterContent;

@Route(path = RouterContent.AROUTER_MAIN)
public class TestArouterMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_arouter_main);
    }
}
