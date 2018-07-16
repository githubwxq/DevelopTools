package com.example.aroutertestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.router.RouterContent;

@Route(path = RouterContent.AROUTER_MAIN)
public class TestArouterMainActivity extends AppCompatActivity {
    @Autowired
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_arouter_main);
        ARouter.getInstance().inject(this);
        String wxq = getIntent().getStringExtra("wxq");

        TextView tv_name= (TextView) findViewById(R.id.tv_name);
        tv_name.setText(name);

        ToastUtils.showLong(wxq + "");

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //前往网络模块
                ARouter.getInstance()
                        .build(RouterContent.NETTEST_MAIN)
                        .withString("from","testarouter")
                        .navigation();
            }
        });

    }
}
