package com.wxq.commonlibrary.weiget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wxq.commonlibrary.R;
public class DialogManager {
    private static DialogManager dialogManager = null;
    private Dialog loadingDialog = null;

    public static DialogManager getInstance() {
        if (dialogManager == null) {
            dialogManager = new DialogManager();
        }
        return dialogManager;
    }

    public Dialog createLoadingDialog(Context context, String msg) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            // 得到加载view
            View v = inflater.inflate(R.layout.common_progressdialog_no_deal, null);
            // 加载布局
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
            // 提示文字
            TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
            ImageView img = (ImageView) v.findViewById(R.id.img);
            img.setImageDrawable(new ProgressDrawable());
            ((ProgressDrawable) img.getDrawable()).start();

            // 设置加载信息
            tipTextView.setText(msg);
            // 创建自定义样式dialog
            loadingDialog = new Dialog(context, R.style.common_loading_dialog);
            // 可以用“返回键”取消
            loadingDialog.setCancelable(true);
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    loadingDialog = null;
                    if (onCancelListener != null) {
                        onCancelListener.onCancel(dialog);
                        onCancelListener = null;//防止其他页面的dialog消失后也会调用该回调
                    }
                }
            });
        } catch (Exception e) {
            loadingDialog = new ProgressDialog(context);
        }
        return loadingDialog;

    }

    public void cancelDialog() {
        try {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShow() {
        return loadingDialog != null && loadingDialog.isShowing();
    }

    private OnCancelListener onCancelListener;

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public interface OnCancelListener {
        void onCancel(DialogInterface dialog);
    }
}
