package com.juziwl.uilibrary.dialog;

/**
 * 创建日期：2017/10/21
 * 描述: Dialog点击回调接口
 *
 * @author: zhaoh
 */
public interface CommonDialogClickListener {
    /**
     * 点击确认时的回调方法
     */
    void confrim();

    /**
     * 点击取消时的回调方法
     */
    void cancle();

    /**
     * 点击确认时的回调方法
     */
    void confrim(int num);
}
