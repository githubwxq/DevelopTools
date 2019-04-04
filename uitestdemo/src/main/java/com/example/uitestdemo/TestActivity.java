package com.example.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.example.inject.compile.InjectView;
import com.example.inject.runtime.InjectUtils;
import com.example.inject.runtime.OnClick;
import com.example.inject.runtime.OnLongClick;
import com.example.inject.runtime.ViewInject;
import com.example.inject_annotion.BindView;
import com.wxq.commonlibrary.util.ToastUtils;

public class TestActivity extends AppCompatActivity {
    @ViewInject(R.id.tv)
    public TextView textView;

    @OnClick({R.id.tv})
    public void clickTv(View view){
        ToastUtils.showShort("clickTv 测试成功");
    }

    @OnClick({R.id.tv2})
    public void clickTv2(View view){
        ToastUtils.showShort("clickTv2 测试成功");
    }
    @OnLongClick({R.id.tv2,R.id.tv})
    public boolean clickLong(View view){
        ToastUtils.showShort("长按测试 测试成功");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        InjectView.bind(this);
        InjectUtils.inject(this);
        SpannableStringBuilder builder = (SpannableStringBuilder) Html.fromHtml("<p>34234234__234234__</p>");
        textView.setText(builder);

//        new TestThread().start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                 while (true){
//                     try {
//                         Thread.sleep(60*1000*5);
//                     } catch (InterruptedException e) {
//                         e.printStackTrace();
//                     }
//
//                 }
//            }
//        }).start();

    }

    private class  TestThread extends Thread{

        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
