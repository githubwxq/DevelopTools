package com.example.uitestdemo.fragment.components.edit;


import com.example.uitestdemo.R;
import com.example.uitestdemo.fragment.components.flowlayout.FlowLayoutFragment;
import com.juziwl.uilibrary.edittext.verify.VerifyCodeEditText;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifyCodeEditTextFragment extends BaseFragment implements VerifyCodeEditText.OnInputListener {

    @BindView(R.id.vcet_1)
    VerifyCodeEditText vcet1;
    @BindView(R.id.vcet_2)
    VerifyCodeEditText vcet2;
    @BindView(R.id.vcet_3)
    VerifyCodeEditText vcet3;
    @BindView(R.id.vcet_4)
    VerifyCodeEditText vcet4;


    public static VerifyCodeEditTextFragment newInstance() {
        VerifyCodeEditTextFragment fragment = new VerifyCodeEditTextFragment();
        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_verify_code_edittext;
    }

    @Override
    protected void initViews() {
        vcet1.setOnInputListener(this);
        vcet2.setOnInputListener(this);
        vcet3.setOnInputListener(this);
        vcet4.setOnInputListener(this);
    }


    @Override
    public void onComplete(String input) {
        showToast("onComplete:" + input);
    }

    @Override
    public void onChange(String input) {
        showToast("onChange:" + input);
    }

    @Override
    public void onClear() {
       showToast("onClear");
    }

    @OnClick(R.id.btn_clear)
    public void onViewClicked() {
        vcet1.clearInputValue();
        vcet2.clearInputValue();
        vcet3.clearInputValue();
        vcet4.clearInputValue();
    }
}
