package com.example.nettestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.wxq.mvplibrary.router.RouterContent;
@Route(path = RouterContent.NETTEST_MAIN)
public class NetTestActivity extends AppCompatActivity {
    TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);
        tv_test= (TextView) findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_test.setText(getIntent().getStringExtra("from"));

            }
        });
    }
}
