package com.juziwl.uilibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.JumpPermissionManagement;
import com.wxq.commonlibrary.util.ScreenUtils;

//
//import com.juziwl.commonlibrary.config.Global;
//import com.juziwl.commonlibrary.utils.DisplayUtils;
//import com.juziwl.commonlibrary.utils.JumpPermissionManagement;


/**
 * 创建日期：2017/10/21
 * 描述:  新项目中共用的一些Dialog弹窗。有两个按钮
 *
 * @author: zhaoh
 */
public class CommonDialog {

    private static CommonDialog instance = null;

    private CommonDialog() {
    }

    public static CommonDialog getInstance() {
        if (instance == null) {
            instance = new CommonDialog();
        }
        return instance;
    }

    Dialog dialog;

    /**
     * 两个按钮的Dialog
     *
     * @param context       上下文
     * @param title         标题栏
     * @param message       正文
     * @param leftStr       左边按钮的文字
     * @param leftListener  左Listener
     * @param rightStr      右边按钮的文字
     * @param rightListener 右Listener
     * @return
     */
    public CommonDialog createDialog(Context context, String title, String message, String leftStr,
                                     View.OnClickListener leftListener, String rightStr, View.OnClickListener rightListener) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dialog = new Dialog(context, R.style.common_textDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.dialog_common, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_content);
            TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
            Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
            Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            } else {
                tvTitle.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            } else {
                tvMessage.setText("");
            }
            if (!TextUtils.isEmpty(leftStr)) {
                btnCancel.setText(leftStr);
            }

                btnConfirm.setBackgroundResource(R.drawable.btn_normal_selector_bg_blue);

            if (!TextUtils.isEmpty(rightStr)) {
                btnConfirm.setText(rightStr);
            }
            btnConfirm.setOnClickListener(v -> {
                dialog.cancel();
                if (rightListener != null) {
                    rightListener.onClick(v);
                }
            });
            btnCancel.setOnClickListener(v -> {
                dialog.cancel();
                if (leftListener != null) {
                    leftListener.onClick(v);
                }
            });
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return instance;
    }

    public CommonDialog createDialog(Context context, String message, String rightStr, View.OnClickListener rightListener) {
        return createDialog(context, context.getString(R.string.kindly_reminder), message, context.getString(R.string.button_cancel),
                null, rightStr, rightListener);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            WindowManager.LayoutParams wl = dialog.getWindow().getAttributes();
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            wl.width = ScreenUtils.getScreenWidth() * 4 / 5;
            dialog.onWindowAttributesChanged(wl);
        }
    }


    public void cancel() {
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPermissionDialog(Activity activity, String message) {
        createDialog(activity, message, "前往设置", v -> JumpPermissionManagement.goToSetting(activity)).show();
    }

}
