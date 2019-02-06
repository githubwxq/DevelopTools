package com.wxq.commonlibrary.bmob;

import android.app.Activity;
import android.content.Context;

import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.weiget.DialogManager;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by CWQ on 2017/4/19.
 */

public abstract class SimpleUpdateListener extends UpdateListener {
    private long startTimeMillis;
    private Context progressDialog;


    public SimpleUpdateListener() {
    }


    public SimpleUpdateListener(Context progressDialog) {
        this.progressDialog = progressDialog;
        onBefore();
    }

    public final void onBefore() {
        startTimeMillis = System.currentTimeMillis();
        DialogManager.getInstance().createLoadingDialog(progressDialog, "正在加载中...").show();
        before();
    }

    @Override
    public void done(final BmobException e) {

        if (e == null) {
            success();
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

    public abstract void success();

    protected void error(BmobException e) {
        ToastUtils.showShort(e.getMessage());
    }

    protected void after() {

    }
}
