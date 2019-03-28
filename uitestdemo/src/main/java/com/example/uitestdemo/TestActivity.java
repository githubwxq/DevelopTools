package com.example.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.example.inject.InjectView;
import com.example.inject_annotion.BindView;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        InjectView.bind(this);
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
