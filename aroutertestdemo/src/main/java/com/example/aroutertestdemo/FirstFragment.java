package com.example.aroutertestdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nettestdemo.R;
import com.wxq.mvplibrary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wxq on 2018/7/20.
 */

public class FirstFragment extends BaseFragment<FirstFragmentPresent> {

    @BindView(R.id.tv_first)
    TextView tvFirst;
    Unbinder unbinder;

    RelativeLayout rlOutFrame;
    Unbinder unbinder1;

    @Override
    protected FirstFragmentPresent initPresenter() {
        return new FirstFragmentPresent(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void commonLoad(View view) {
//        tvFirst.setText("fragment__test1111");
        rlOutFrame= (RelativeLayout) view.findViewById(R.id.rl_out_frame);

    }

    @Override
    public void lazyLoadData(View view) {
        tvFirst.setText("fragment__test1111");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isVisibleToUser) {
            rlOutFrame.setVisibility(View.GONE);
        } else {
            rlOutFrame.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onPause() {
        super.onPause();

    }


    public void setoutlinegone(){
        rlOutFrame.setVisibility(View.GONE);
    }
}
