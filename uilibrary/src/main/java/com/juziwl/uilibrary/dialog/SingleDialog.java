package com.juziwl.uilibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.renderscript.ScriptIntrinsicResize;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.utils.ScreenUtils;

/**
 * @author nat.xue
 * @date 2017/10/26
 * @description 一个按钮的Dialog
 */

public class SingleDialog {
    private Dialog dialog;

    private static SingleDialog instance = null;

    private SingleDialog() {
    }

    public static SingleDialog getInstance() {
        if (instance == null) {
            instance = new SingleDialog();
        }
        return instance;
    }


    public boolean isShow() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {

            return false;
        }

    }

    /**
     * 一个按钮的Dialog
     *
     * @param context     上下文
     * @param title       标题栏
     * @param message     正文
     * @param isCanCancel dialog点击外面能否被取消
     * @return
     */
    public SingleDialog createDialog(Context context, String title, String message, String str,
                                     View.OnClickListener listener, boolean... isCanCancel) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dialog = new Dialog(context, com.juziwl.uilibrary.R.style.common_textDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.dialog_single, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_content);
            TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
            Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setText("");
            }
//            if (Global.loginType == Global.TEACHER) {
//                btnConfirm.setBackgroundResource(R.drawable.btn_normal_selector_bg_blue);
//            }
            if (!TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            } else {
                tvMessage.setText("");
            }

            if (!TextUtils.isEmpty(str)) {
                btnConfirm.setText(str);
            }

            btnConfirm.setOnClickListener(v -> {
                dialog.cancel();
                if (listener != null) {
                    listener.onClick(v);
                }
            });
            if (isCanCancel != null && isCanCancel.length > 0) {
                dialog.setCancelable(isCanCancel[0]);
                dialog.setCanceledOnTouchOutside(isCanCancel[0]);
            } else {
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.setContentView(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return instance;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            WindowManager.LayoutParams wl = dialog.getWindow().getAttributes();
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            wl.width = ScreenUtils.getScreenWidth(dialog.getContext()) * 4 / 5;
            dialog.onWindowAttributesChanged(wl);
        }
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
