package com.luck.picture.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.luck.picture.lib.R;


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

    /**
     * 一个按钮的Dialog
     * @param context 上下文
     * @param title 标题栏
     * @param message 正文
     * @param listener listener
     * @return
     */
    public Dialog createDialog(Context context, String title, String message, String str, final View.OnClickListener listener) {
        dialog = new Dialog(context, R.style.Theme_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.picture_dialog_single, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_content);
            TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
            Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setText("");
            }
            if (!TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            } else {
                tvMessage.setText("");
            }
            if (!TextUtils.isEmpty(str)) {
                btnConfirm.setText(str);
            }
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dialog;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
