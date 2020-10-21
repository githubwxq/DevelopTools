package com.example.uitestdemo.fragment.components.button;

import android.view.View;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.button.CountDownButton;
import com.juziwl.uilibrary.button.CountDownButtonHelper;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;


public class ButtonStyleFragment extends BaseFragment {

    @BindView(R.id.bt_countdown3)
    CountDownButton btCountdown3;

    public static ButtonStyleFragment newInstance() {
        ButtonStyleFragment fragment = new ButtonStyleFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_button_style;
    }


    CountDownButtonHelper  mCountDownHelper3;
    @Override
    protected void initViews() {
          mCountDownHelper3 = new CountDownButtonHelper(btCountdown3, 15);
        mCountDownHelper3.setOnCountDownListener(new CountDownButtonHelper.OnCountDownListener() {
            @Override
            public void onCountDown(int time) {
                btCountdown3.setText("还剩：" + time + "秒");
            }

            @Override
            public void onFinished() {
                btCountdown3.setText("点击重试");
                showToast("时间到！");
            }
        });
        btCountdown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownHelper3.start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        mCountDownHelper3.cancel();
        btCountdown3.cancelCountDown();
        super.onDestroyView();
    }
}