package com.example.uitestdemo.fragment.components.dialog;

import android.view.View;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.dialog.PayDialog;
import com.juziwl.uilibrary.dialog.PayDialogEasy;
import com.juziwl.uilibrary.edittext.PayPwdEditText;
import com.juziwl.uilibrary.textview.SuperTextView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文字滚动控件
 *
 * @author XUE
 * @date 2017/9/13 10:10
 */

public class PayDialogFragment extends BaseFragment {


    @BindView(R.id.pay_one)
    SuperTextView payOne;
    @BindView(R.id.pay_two)
    SuperTextView payTwo;

    public static PayDialogFragment newInstance() {
        PayDialogFragment fragment = new PayDialogFragment();
        return fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_pay_dialog;
    }

    @OnClick({R.id.pay_one, R.id.pay_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_one:
                PayDialogEasy   dialog = new PayDialogEasy(mContext);
                dialog.setMoney(100);
                dialog.getPayPwdEditText().setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
                    @Override
                    public void onFinish(String password) {
                        dialog.dismiss();
                      showToast(password);
                    }
                });
                dialog.show();
                break;
            case R.id.pay_two:
                PayDialog  payDialog=new PayDialog(mContext,"标题","详情","11.15");
                payDialog.setGetStrListener(new PayDialog.GetStrListener() {
                    @Override
                    public void getStrListener(String pwd) {
                        showToast(pwd);
                    }
                });
                payDialog.setCanceledOnTouchOutside(true);
                payDialog.show();
                break;
        }
    }
}
