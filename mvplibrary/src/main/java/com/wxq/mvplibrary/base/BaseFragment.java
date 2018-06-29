package com.wxq.mvplibrary.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.juziwl.uilibrary.dialog.DialogManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.baserx.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.wxq.mvplibrary.baserx.Event;
/**
 * Created by long on 2016/5/31.
 * 碎片基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseView {


    public T mPresenter;

    Unbinder unbinder;

    private Dialog mDialog;

    protected Context mContext;

    public boolean hideflag = false;

    //防止fragment重叠
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    public String fragmentTitle;
    private boolean isVisible;

    private boolean isPrepared;
    protected boolean isFirstLoad = true;

    protected RxPermissions rxPermissions = null;
    //缓存Fragment view
    protected View mRootView;
    private boolean waitingShowToUser = false;
    private FragmentTransaction ft;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

//        if (savedInstanceState != null) {
//            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
//            ft = getFragmentManager().beginTransaction();
//            if (isSupportHidden) {
//                ft.hide(this);
//            } else {
//                ft.show(this);
//            }
//            ft.commit();
//        }

    }

    protected abstract T initPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null)
            mPresenter.unDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            unbinder = ButterKnife.bind(this, mRootView);
            mPresenter = initPresenter();
            initViews();
            rxPermissions = new RxPermissions((Activity) mContext);
            initBroadcastAction();//初始化广播
            initRxBus();
        }
        return mRootView;
    }

    private void initRxBus() {

        RxBus.getDefault().take()
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY)).subscribe(event -> {
            dealWithRxEvent(event.action, event);
        });;

    }

    public void dealWithRxEvent(int action, Event event) {

    }


    // 通用的加载框
    @Override
    public void showLoadingDialog() {
        DialogManager.getInstance().createLoadingDialog(getContext(), "正在加载中...").show();
    }

    @Override
    public void dismissLoadingDialog() {
        DialogManager.getInstance().cancelDialog();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        commonLoad(mRootView);  //比initwidget后执行 initwidget初始化控件 实现类用butterknife实现
        lazyLoad(mRootView);
    }

    /**
     * 普通加载  获取参数argument 获取网络数据
     */
    public void commonLoad(View view) {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 如果自己是显示状态，但父Fragment却是隐藏状态，就把自己也改为隐藏状态，并且设置一个等待显示标记
        if (getUserVisibleHint()) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                waitingShowToUser = true;
                super.setUserVisibleHint(false);
            }
        }
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            // 父Fragment还没显示，你着什么急
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                waitingShowToUser = true;
                super.setUserVisibleHint(false);
//                isVisible = false;
//                onInvisible();
            } else {
                isVisible = true;
                onVisible();
            }
        }
        if (getActivity() != null) {
            @SuppressLint("RestrictedApi")
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof BaseFragment) {
                            BaseFragment childBaseFragment = (BaseFragment) childFragment;
                            if (childBaseFragment.isWaitingShowToUser()) {
                                childBaseFragment.setWaitingShowToUser(false);
                                childFragment.setUserVisibleHint(true);
                            }
                        }
                    }
                }
            } else {
                // 将所有正在显示的子Fragment设置为隐藏状态，并设置一个等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof BaseFragment) {
                            BaseFragment childBaseFragment = (BaseFragment) childFragment;
                            if (childFragment.getUserVisibleHint()) {
                                childBaseFragment.setWaitingShowToUser(true);
                                childFragment.setUserVisibleHint(false);
                            }
                        }
                    }
                } else {
                    isVisible = false;

                }
            }
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }




    public void onVisible() {
        if (mRootView != null) {
            lazyLoad(mRootView);
        }
    }


    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    private void lazyLoad(View view) {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            //if (!isAdded() || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        lazyLoadData(view);
    }

    public void lazyLoadData(View view) {
    }

    // 处理系统发出的广播
    public BroadcastReceiver broadcastReceiver = null, localBroadcastReceiver = null;

    //注册广播
    public void initBroadcastAction() {
        List<String> broadcastAction = getBroadcastAction();
        if (broadcastAction != null && broadcastAction.size() > 0) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    dealWithBroadcastAction(context, intent);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            for (String action : broadcastAction) {
                intentFilter.addAction(action);
            }
            mContext.registerReceiver(broadcastReceiver, intentFilter);
        }

        List<String> localBroadcastAction = getLocalBroadcastAction();
        if (localBroadcastAction != null && !localBroadcastAction.isEmpty()) {
            localBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    dealWithBroadcastAction(context, intent);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            for (String action : localBroadcastAction) {
                intentFilter.addAction(action);
            }
            LocalBroadcastManager.getInstance(mContext).registerReceiver(localBroadcastReceiver, intentFilter);
        }
    }

    public boolean isWaitingShowToUser() {
        return waitingShowToUser;
    }

    public void setWaitingShowToUser(boolean waitingShowToUser) {
        this.waitingShowToUser = waitingShowToUser;
    }

    /**
     * 子类添加的action
     */
    public List<String> getBroadcastAction() {
        return null;
    }

    public List<String> getLocalBroadcastAction() {  //钩子函数
        return null;
    }

    public void dealWithBroadcastAction(Context context, Intent intent) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
//        只要上面的9行代码！ FragmentState没帮我们保存Hidden状态，那就我们自己来保存，在页面重启后，我们自己来决定Fragment是否显示！
//        解决思路转变了，由Activity/父Fragment来管理子Fragment的Hidden状态转变为 由Fragment自己来管理自己的Hidden状态！
    }

    // 通用的加载框
    public void showLoading() {
        if (mDialog == null) {
            mDialog = DialogManager.getInstance().createLoadingDialog(getActivity(), "正在加载中...");
        }
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }


    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }


    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();


    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(message);
    }

}
