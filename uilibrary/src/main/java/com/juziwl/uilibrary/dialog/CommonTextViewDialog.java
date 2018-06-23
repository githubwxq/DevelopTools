package com.juziwl.uilibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juziwl.uilibrary.R;

/**
 * Created by haojx on 2017/10/23.
 *
 * @author Neil
 * @version exue 3.0
 * @date 2017/10/23 14:26
 * @description
 */
public class CommonTextViewDialog {

    private Dialog dialog;

    private static CommonTextViewDialog instance = null;

    private CommonTextViewDialog() {
    }

    public static CommonTextViewDialog getInstance() {
        if (instance == null) {
            instance = new CommonTextViewDialog();
        }
        return instance;
    }

    /**
     *
     * @param context 上下文
     * @param content 标题
     * @param message 消息内容
     * @param confirmStr 确认的资源图片
     * @param listener 点击的回调监听
     * @return
     */
    public Dialog createDiaog(Context context, String content, String message, String confirmStr,CommonDialogClickListener listener) {
        dialog = new Dialog(context, R.style.common_textDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            View view = inflater.inflate(R.layout.dialog_textview, null);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
            TextView tvConfirm = (TextView) view.findViewById(R.id.iv_confrim);
            TextView tvCancel = (TextView) view.findViewById(R.id.iv_cancle);
            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(content);
            } else {
                tvContent.setText("");
            }
            if (!TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            } else {
                tvMessage.setText("");
            }
            if (!TextUtils.isEmpty(confirmStr)) {
                tvConfirm.setText(confirmStr);
            } else {
                tvConfirm.setText("");
            }
            tvConfirm.setOnClickListener(v -> {
                if (listener != null) {
                    listener.confrim();
                }
            });
            tvCancel.setOnClickListener(v -> {
                if (listener != null) {
                    listener.cancle();
                }
            });
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
            dialog.dismiss();
        }
    }

}
