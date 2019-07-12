package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;
import com.wxq.developtools.activity.EditPersonActivity;
import com.wxq.developtools.activity.MyCollectionActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MySelfFragment extends BaseFragment {

    @BindView(R.id.heard_icon)
    ImageView heardIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_pingzheng)
    RelativeLayout rlPingzheng;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    Unbinder unbinder;

    public static MySelfFragment newInstance() {
        MySelfFragment fragment = new MySelfFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_myself;
    }

    @Override
    protected void initViews() {

    }


    @OnClick({R.id.heard_icon, R.id.tv_name, R.id.rl_pingzheng, R.id.rl_collect, R.id.rl_order, R.id.rl_help, R.id.rl_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.heard_icon:
                EditPersonActivity.navToActivity(mContext);
                break;
            case R.id.tv_name:
                EditPersonActivity.navToActivity(mContext);
                break;
            case R.id.rl_pingzheng:

                break;
            case R.id.rl_collect:
                MyCollectionActivity.navToActivity(mContext);
                break;
            case R.id.rl_order:

                break;
            case R.id.rl_help:

                break;
            case R.id.rl_about:

                break;
        }
    }
}
