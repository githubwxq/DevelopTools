package com.example.nettestdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.nettestdemo.R;
import com.wxq.mvplibrary.router.RouterContent;

import static com.wxq.mvplibrary.router.RouterContent.NETTEST_MAIN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();
        ARouter.init(getApplication());

        TextView textView= (TextView) findViewById(R.id.tv_goto);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,NetTestActivity.class));
                ARouter.getInstance().build(RouterContent.NETTEST_MAIN).navigation();
            }
        });


    }
}
