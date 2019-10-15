package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.model.UserInfo;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.activity.CertificateActivity;
import com.wxq.developtools.activity.EditPersonActivity;
import com.wxq.developtools.activity.MyCollectionActivity;
import com.wxq.developtools.api.KlookApi;

import butterknife.BindView;
import butterknife.OnClick;


public class MySelfFragment extends BaseFragment {

    public static final int PICUPDATE = 998;
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

    String picPath="";


    public static MySelfFragment newInstance() {
        MySelfFragment fragment = new MySelfFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onFragmentResume() {
      //更新用户信息
        getUserInfo();
        ImmersionBar.with(this) .statusBarColor(com.wxq.commonlibrary.R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    private void getUserInfo() {
        Api.getInstance()
                .getApiService(KlookApi.class).findUserById()
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<UserInfo>() {
                    @Override
                    protected void onSuccess(UserInfo data) {
                        AllDataCenterManager.getInstance().userInfo=data;
                        // 显示试图
                        tvName.setText(data.phone);
                        picPath=data.head;
                        LoadingImgUtil.loadimg(picPath,heardIcon,true);
                    }
                });
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
                EditPersonActivity.navToActivity(mContext,picPath);
                break;
            case R.id.tv_name:
                EditPersonActivity.navToActivity(mContext,picPath);
                break;
            case R.id.rl_pingzheng:
                CertificateActivity.navToActivity(getActivity());
                break;
            case R.id.rl_collect:
                MyCollectionActivity.navToActivity(mContext);
                break;

            case R.id.rl_order:
                // 我的订单页面


                break;
            case R.id.rl_help:


                break;
            case R.id.rl_about:


                break;
        }
    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        if (action==PICUPDATE) {
            LoadingImgUtil.loadimg(event.getObject(),heardIcon,true);
        }
    }



}
