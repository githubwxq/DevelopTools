package com.example.uitestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import com.juziwl.uilibrary.notification.NotificationUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.model.User;
import com.wxq.mvplibrary.router.RouterContent;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UIMainActivity extends BaseActivity {


    @BindView(R.id.tv_test)
    TextView tvTestUi;

    int a = 0;

    User user;

    @Override
    protected void initViews() {
        topHeard.setRightText("测试顶部栏目");
        tvTestUi.setText("点击进入ui模块");
        tvTestUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ARouter.getInstance()
//                        .build(RouterContent.UI_MAIN)
//                        .navigation();
//
//                Intent intent=new Intent(UIMainActivity.this,AudioTestActivity.class);
//                startActivity(intent);
                List<String> list = new ArrayList<>();
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");

                for (String s : list) {
                    String current = s;

                }
                a++;
//                 int a=0;
//             boolean judgeNull=  user.accessToken.equals("1");
                NotificationUtils.showCommonNofition(UIMainActivity.this,"id","name");
            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_uimain;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
