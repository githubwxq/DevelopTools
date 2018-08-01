package com.example.aroutertestdemo;

import android.widget.TextView;

import com.example.nettestdemo.R;
import com.wxq.mvplibrary.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by wxq on 2018/7/20.
 */

public class SecondFragment extends BaseFragment<SecondFragmentPresent> {

    @BindView(R.id.tv_first)
    TextView tvFirst;
    Unbinder unbinder;

    @Override
    protected SecondFragmentPresent initPresenter() {
        return new SecondFragmentPresent(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initViews() {
//        tvFirst.setText("fragment");
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
