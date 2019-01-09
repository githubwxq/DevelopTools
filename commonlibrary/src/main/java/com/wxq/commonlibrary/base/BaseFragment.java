package com.wxq.commonlibrary.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.baserx.RxBus;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.weiget.DialogManager;

/**
 * Created by long on 2016/5/31.
 * 碎片基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseView {

    public T mPresenter;
    Unbinder unbinder;
    private Dialog mDialog;
    protected Context mContext;
    //防止fragment重叠
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    public String fragmentTitle;
    protected RxPermissions rxPermissions = null;
    //缓存Fragment view
    protected View mRootView;


    protected boolean mIsFirstVisible = true;
    protected boolean isViewCreated = false;
    protected boolean currentVisibleState = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        mContext = getActivity();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisible) {
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }


    /**
     * 统一处理 显示隐藏
     *
     * @param visible
     */
    public void dispatchUserVisibleHint(boolean visible) {
        //当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment getUserVisibleHint = true
        //但当父 fragment 不可见所以 currentVisibleState = false 直接 return 掉
        // 这里限制则可以限制多层嵌套的时候子 Fragment 的分发
        if (visible && isParentInvisible()) return;
        //此处是对子 Fragment 不可见的限制，因为 子 Fragment 先于父 Fragment回调本方法 currentVisibleState 置位 false
        // 当父 dispatchChildVisibleState 的时候第二次回调本方法 visible = false 所以此处 visible 将直接返回
        if (currentVisibleState == visible) {
            return;
        }
        currentVisibleState = visible;
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                lazyLoadData(null);
            }
            onFragmentResume();
            dispatchChildVisibleState(true);
        } else {
            dispatchChildVisibleState(false);
            onFragmentPause();
        }
    }

    /**
     * 当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment 的唯一或者嵌套 VP 的第一 fragment 时 getUserVisibleHint = true
     * 但是由于父 Fragment 还进入可见状态所以自身也是不可见的， 这个方法可以存在是因为庆幸的是 父 fragment 的生命周期回调总是先于子 Fragment
     * 所以在父 fragment 设置完成当前不可见状态后，需要通知子 Fragment 我不可见，你也不可见，
     * <p>
     * 因为 dispatchUserVisibleHint 中判断了 isParentInvisible 所以当 子 fragment 走到了 onActivityCreated 的时候直接 return 掉了
     * <p>
     * 当真正的外部 Fragment 可见的时候，走 setVisibleHint (VP 中)或者 onActivityCreated (hide show) 的时候
     * 从对应的生命周期入口调用 dispatchChildVisibleState 通知子 Fragment 可见状态
     *
     * @param visible
     */
    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment child : fragments) {
                if (child instanceof BaseFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    ((BaseFragment) child).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    /**
     * 用于分发可见时间的时候父获取 fragment 是否隐藏
     *
     * @return true fragment 不可见， false 父 fragment 可见
     */
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof BaseFragment ) {
            BaseFragment fragment = (BaseFragment) parentFragment;
            return !fragment.isSupportVisible();
        }else {
            return false;
        }
    }

    public boolean isSupportVisible() {
        return currentVisibleState;
    }

    public void lazyLoadData(View view) {
        Logger.d(getClass().getSimpleName() + "  对用户第一次可见");
    }


    public void onFragmentResume() {
        Logger.d(getClass().getSimpleName() + "  对用户可见");

    }

    public void onFragmentPause() {
        Logger.d(getClass().getSimpleName() + "  对用户不可见");

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
        initViews();
        commonLoad(mRootView);  //比initwidget后执行 initwidget初始化控件 实现类用butterknife实现
    }

    /**
     * 普通加载  获取参数argument 获取网络数据
     */
    public void commonLoad(View view) {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        // !isHidden() 默认为 true  在调用 hide show 的时候可以使用
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见
        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立,等从别的界面回到这里后会使用 onFragmentResume 通知可见
        // 对于非默认 tab mIsFirstVisible = true 会一直保持到选择则这个 tab 的时候，因为在 onActivityCreated 会返回 false
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.d(getClass().getSimpleName() + "  onHiddenChanged dispatchChildVisibleState  hidden " + hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
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


    @Override
    public <T> LifecycleTransformer<T> bindDestory() {
        return this.bindToLife();
    }
}
