package com.example.uitestdemo;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.juziwl.uilibrary.X5utils.X5WebView;
import com.juziwl.uilibrary.edittext.SuperEditText;
import com.juziwl.uilibrary.notification.NotificationUtils;
import com.juziwl.uilibrary.progressbar.MyProgressBarSecond;
import com.juziwl.uilibrary.recycler.xrecyclerview.SuperRecyclerView;
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
    @BindView(R.id.et_superedit)
    SuperEditText etSuperedit;


    SuperRecyclerView superRecyclerView;


    @Override
    protected void initViews() {
        topHeard.setRightText("测试顶部栏目");
        tvTestUi.setText("点击进入ui模块");
        webview.setVisibility(View.GONE);
        etSuperedit.setPassWordStyle(true);
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

                Intent intent = new Intent(UIMainActivity.this, TestNativeWebViewActivity.class);
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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    etSuperedit.clearFocus();
                }

//                KeyboardUtils.hideSoftInput(v);

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
