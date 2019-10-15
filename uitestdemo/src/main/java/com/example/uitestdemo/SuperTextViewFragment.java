package com.example.uitestdemo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruffian.library.widget.RTextView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SuperTextViewFragment extends BaseFragment {

    @BindView(R.id.tv_testcolor)
    RTextView tvTestcolor;


    @BindView(R.id.tv_select)
    RTextView tvSelect;

    @BindView(R.id.tv_bg)
    RTextView tvBg;
    Unbinder unbinder;

    Unbinder unbinder1;
    @BindView(R.id.rl_list)
    RecyclerView rlList;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_super_text_view;
    }

    @Override
    protected void initViews() {

    }

    boolean isSelect = false;

    @OnClick({R.id.tv_testcolor, R.id.tv_select, R.id.tv_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_testcolor:
//                isSelect
                tvTestcolor.setSelected(isSelect = !isSelect);
                break;
            case R.id.tv_select:
//                tvSelect.setSelected(isSelect=!isSelect);
                break;
            case R.id.tv_bg:
                tvBg.setEnabled(isSelect = !isSelect);
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
