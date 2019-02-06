package com.wxq.commonlibrary.bmob;


import android.content.Context;

import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.weiget.DialogManager;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by CWQ on 2017/4/19.
 */

public abstract class SimpleUploadBatchListener implements UploadBatchListener {
    private long startTimeMillis;
    private Context progressDialog;

    int size;

    public SimpleUploadBatchListener(Context progressDialog, int size) {
        this.progressDialog = progressDialog;
        this.size = size;
        onBefore();
    }

    public final void onBefore() {
        startTimeMillis = System.currentTimeMillis();
//        DialogManager.getInstance().createLoadingDialog(progressDialog, "正在加载中...").show();
        before();
    }


    public final void onAfter() {
//        DialogManager.getInstance().cancelDialog();
        after();
    }

    private long getTimeDif() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    protected void before() {

    }

    public abstract void success(List<BmobFile> list, List<String> urls);

    protected void error(BmobException e) {
        ToastUtils.showShort(e.getMessage());
    }

    protected void after() {

    }

    @Override
    public void onSuccess(List<BmobFile> list, List<String> urls) {
        if (urls.size() == size) {//如果数量相等，则代表文件全部上传完成
            //do something
            success(list, urls);
        }
    }

    @Override
    public void onProgress(int i, int i1, int i2, int i3) {

    }

    @Override
    public void onError(int i, String s) {
        ToastUtils.showShort(s);
        after();
    }
}
