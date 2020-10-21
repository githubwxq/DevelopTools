package com.example.uitestdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.basebanner.callback.PageHelperListener;
import com.juziwl.uilibrary.basebanner.view.BannerViewPager;
import com.ruffian.library.widget.RTextView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.viewpage)
    BannerViewPager viewpage;



    public static SuperTextViewFragment newInstance() {
        SuperTextViewFragment fragment = new SuperTextViewFragment();
        return fragment;
    }



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
        List<String> list=new ArrayList<>();
        list.add("11");
        list.add("222");
        list.add("333");
        list.add("4444");
        list.add("5555");

        viewpage.setPageData(getActivity().getLifecycle(), list, R.layout.adapter_item, new PageHelperListener<String>() {
            @Override
            public void getItemView(View mCurrentContent, String item, int position) {
               TextView textView= mCurrentContent.findViewById(R.id.tv_name);
                textView.setText("条目"+item);
            }
        }).startAnim();
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
