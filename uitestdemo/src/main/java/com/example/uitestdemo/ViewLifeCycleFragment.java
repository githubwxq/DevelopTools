package com.example.uitestdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juziwl.uilibrary.customview.TestCustomView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ViewLifeCycleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewLifeCycleFragment extends BaseFragment {

    @BindView(R.id.tv_view)
    TestCustomView tvView;
    @BindView(R.id.control)
    Button control;
    Unbinder unbinder;

    public static ViewLifeCycleFragment newInstance() {
        ViewLifeCycleFragment fragment = new ViewLifeCycleFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_view_life_cycle;
    }

    @Override
    protected void initViews() {
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvView.start();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
