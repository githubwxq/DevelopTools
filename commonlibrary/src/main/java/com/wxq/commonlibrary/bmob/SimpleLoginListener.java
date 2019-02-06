package com.wxq.commonlibrary.bmob;


import android.content.Context;

import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.weiget.DialogManager;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by CWQ on 2017/4/20.
 */

public abstract class SimpleLoginListener<T> extends LogInListener<T> {


    private long startTimeMillis;
    private Context context;


    public SimpleLoginListener(Context context) {
        this.context = context;
        onBefore();
    }

    public final void onBefore() {
        startTimeMillis = System.currentTimeMillis();
        DialogManager.getInstance().createLoadingDialog(context, "正在加载中...").show();
        before();
    }

    @Override
    public final void done(final T t, final BmobException e) {

        if (e == null) {
            success(t);
        } else {

            error(e);
        }
        onAfter();

    }

    public final void onAfter() {
        DialogManager.getInstance().cancelDialog();
        after();
    }

    private long getTimeDif() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    protected void before() {

    }

    public abstract void success(T t);

    protected void error(BmobException e) {
        ToastUtils.showShort(e.getMessage());
    }

    protected void after() {

    }
}
