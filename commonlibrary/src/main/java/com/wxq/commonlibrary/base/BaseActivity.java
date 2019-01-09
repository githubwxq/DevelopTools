package com.wxq.commonlibrary.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.wxq.commonlibrary.util.AppManager;
import com.wxq.commonlibrary.util.RxHelp;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.RxBus;
import com.wxq.commonlibrary.baserx.RxBusManager;
import com.wxq.commonlibrary.weiget.DialogManager;
import com.wxq.commonlibrary.weiget.TopBarHeard;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {
    public String token = "";
    private Unbinder unbinder;
    Disposable disposable;
    public T mPresenter;
    public Context context;
    private BroadcastReceiver broadcastReceiver = null;
    private BroadcastReceiver localBroadcastReceiver = null;
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    public RxPermissions rxPermissions;
    public TopBarHeard topHeard;


    /**
     * 默认显示顶部top栏
     */
    public boolean needHeardLayout = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        needHeardLayout=isNeedHeardLayout();
        if (needHeardLayout) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            topHeard = new TopBarHeard(this);
            linearLayout.addView(topHeard);
            linearLayout.addView(LayoutInflater.from(this).inflate(attachLayoutRes(), null), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            setContentView(linearLayout);
        } else {
            setContentView(attachLayoutRes());
        }
        unbinder = ButterKnife.bind(this);
        context = this;
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        AppManager.getInstance().addActivity(this);
        rxPermissions = new RxPermissions(this);
        initViews();
        mPresenter = initPresent();
        //数据初始化
        if (mPresenter != null) {
            mPresenter.initEventAndData();
        }
        //注册广播
        initBroadcastAndLocalBroadcastAction();
        // 注册rxbus
        initRxBus();
        // arouter 依赖注入
        ARouter.getInstance().inject(this);
    }

    private void initRxBus() {
        disposable = RxBusManager.getInstance().registerEvent(Event.class, new Consumer<Event>() {
            @Override
            public void accept(Event event) {
                dealWithRxEvent(event.action, event);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Logger.e("rxbus传递出现异常");
                if (throwable != null) {
                    Logger.e(throwable.getMessage());
                }
            }
        });
    }

    protected abstract void initViews();

    protected abstract int attachLayoutRes();

    protected abstract T initPresent();

    public void dealWithBroadcastAction(Context context, Intent intent) {

    }

    public void dealWithRxEvent(int action, Event event) {

    }


    /**
     * 返回类型就写List<String>，不要改成ArrayList<String>
     */
    public List<String> getBroadcastAction() {  //钩子函数
        return null; //默认返回空之类可以添加
    }

    /**
     * 返回类型就写List<String>，不要改成ArrayList<String>
     */
    public List<String> getLocalBroadcastAction() {  //钩子函数
        return null; //默认返回空之类可以添加
    }

    //本地广播和系统广播初始化
    private void initBroadcastAndLocalBroadcastAction() {
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
            registerReceiver(broadcastReceiver, intentFilter);
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
            LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReceiver, intentFilter);
        }
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        this.bindUntilEvent(ActivityEvent.DESTROY);
        return this.<T>bindToLifecycle();
    }

    @Override
    public <T> LifecycleTransformer<T> bindDestory() {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }

    @Override
    public void showLoadingDialog() {
        DialogManager.getInstance().createLoadingDialog(this, "正在加载中...").show();
    }

    @Override
    public void dismissLoadingDialog() {
        DialogManager.getInstance().cancelDialog();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(message);
    }

    public boolean isNeedHeardLayout() {
        return   true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

//    ui相关辅助操作
    public void click(View view, Consumer<Object> consumer){
        RxHelp.click(view,consumer);
    }


}
