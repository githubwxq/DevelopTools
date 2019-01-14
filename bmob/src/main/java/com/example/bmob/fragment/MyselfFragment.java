package com.example.bmob.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.contract.MyselfContract;
import com.example.bmob.presenter.MyselfFragmentPresenter;
import com.wxq.commonlibrary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建日期：2019 1 14
 * 描述:我的页面
 *
 * @author:wxq
 */
public class MyselfFragment extends BaseFragment<MyselfContract.Presenter> implements MyselfContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R2.id.iv_heard_pic)
    ImageView ivHeardPic;
    Unbinder unbinder;

    public static MyselfFragment getInstance(String parmer) {
        MyselfFragment fragment = new MyselfFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_myself;
    }

    @Override
    protected void initViews() {
        ivHeardPic.setImageResource(R.mipmap.common_exaoyuan);

    }

    @Override
    protected MyselfContract.Presenter initPresenter() {
        return new MyselfFragmentPresenter(this);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
