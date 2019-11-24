package com.example.sdktestdemo;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.router.RouterContent;

import butterknife.BindView;

@Route(path = RouterContent.SDK_MAIN)
public class Main2Activity extends BaseActivity {

    @BindView(R2.id.tv_test)
    TextView tvTest;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        //绑定初始化ButterKnife
//        ButterKnife.bind(this);
//        tvTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showShort("1222222");
//            }
//        });
//
//    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main2;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

}
