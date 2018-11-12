package com.example.aroutertestdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wxq.mvplibrary.router.RouterContent;
import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = RouterContent.AROUTER_MAIN)
public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.tv_goto)
    TextView tvGoto;
    @BindView(R2.id.fl_content)
    FrameLayout flContent;

    FirstFragment firstFragment;
    SecondFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        ARouter.openLog();     // 打印日志
//        ARouter.openDebug();
//        ARouter.init(getApplication());

        TextView textView = (TextView) findViewById(R.id.tv_goto);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,NetTestActivity.class));
//                ARouter.getInstance().build(RouterContent.AROUTER_MAIN).navigation();

//                firstFragment.setoutlinegone();

                //前往网络模块
                ARouter.getInstance()
                        .build(RouterContent.AROUTER_TEST)
                        .withString("wxq","123")
                        .navigation();

            }
        });

        addFragment(firstFragment=new FirstFragment(),"first");
        addFragment(secondFragment=new SecondFragment(),"second");


    }




    public void addFragment(Fragment fragment, String tag) {
        // 开启事务
        FragmentTransaction beginTransaction =  getSupportFragmentManager()
                .beginTransaction();
        // 执行事务,添加Fragment
        beginTransaction.add(R.id.fl_content, fragment, tag);
        // 添加到回退栈,并定义标记
        beginTransaction.addToBackStack(tag);
        // 提交事务
        beginTransaction.commit();


    }

}
