package com.example.uitestdemo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.juziwl.uilibrary.X5utils.X5WebView;
import com.juziwl.uilibrary.notification.NotificationUtils;
import com.juziwl.uilibrary.progressbar.MyProgressBarSecond;
import com.wxq.commonlibrary.util.ClipboardUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UIMainActivity extends BaseActivity {


    @BindView(R.id.tv_test)
    TextView tvTestUi;

    @BindView(R.id.tv_test_clickManager)
    TextView tv_test_clickManager;


    int a = 0;

    User user;
    @BindView(R.id.progress_bar)
    MyProgressBarSecond progressBar;
    @BindView(R.id.webview)
    X5WebView webview;

    @Override
    protected void initViews() {
        topHeard.setRightText("测试顶部栏目");
        tvTestUi.setText("点击进入ui模块");
        webview.setVisibility(View.GONE);


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
                NotificationUtils.showCommonNofition(UIMainActivity.this, "id", "name");


                //设置剪切板信息

                ClipboardUtils.copyText(UIMainActivity.this, "wxwxqwxqwxqwxqwxqwxqwxqwxqwxqq");
                progressBar.init();
                webview.setVisibility(View.GONE);
            }
        });


        tv_test_clickManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击 获取剪切板信息
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                CharSequence sequence = clipboardManager.getPrimaryClip().getItemAt(0).getText();

                ToastUtils.showShort(sequence);
//                webview.setVisibility(View.VISIBLE);
//
//                webview.loadUrl("https://github.com/YoKeyword/IndexableRecyclerView");

                 Intent intent=new Intent(UIMainActivity.this,TestNativeWebViewActivity.class);
                 startActivity(intent);


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
