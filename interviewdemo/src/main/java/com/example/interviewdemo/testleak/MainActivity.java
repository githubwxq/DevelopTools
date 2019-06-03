package com.example.interviewdemo.testleak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.interviewdemo.R;

public class MainActivity extends AppCompatActivity {

    TextView tv_test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test=findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMianShi();
            }
        });


    }


    /**
     * 测试面试题目
     */
    private void testMianShi() {
       startActivity(new Intent(this, ThreadLeakActivity.class));
    }
}
