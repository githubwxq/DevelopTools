//package com.juziwl.uilibrary.utils;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
////
////import com.juziwl.commonlibrary.config.Global;
////import com.juziwl.commonlibrary.utils.DisplayUtils;
//import com.juziwl.uilibrary.R;
//import com.wxq.commonlibrary.util.ScreenUtils;
//
///**
// * 创建日期：2018/3/8
// * 描述: 右上角点击点击消失的Dialog
// *
// * @author: zhaoh
// */
//public class SpecialSingleDialog {
//
//    private Dialog dialog = null;
//    private volatile static SpecialSingleDialog singleDialog = null;
//
//    private SpecialSingleDialog() {
//    }
//
//    public static SpecialSingleDialog getInstance() {
//        if (singleDialog == null) {
//            singleDialog = new SpecialSingleDialog();
//        }
//        return singleDialog;
//    }
//
//    public SpecialSingleDialog createSpecialSingleDialog(Context context, String title, String message, String btnText, View.OnClickListener confirm) {
//        if (dialog != null && dialog.isShowing()) {
//            try {
//                dialog.cancel();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        dialog = new Dialog(context, R.style.common_textDialogStyle);
//        View view = View.inflate(context, R.layout.dialog_specal_single, null);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tv_content);
//        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
//        ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancle);
//        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
//        if (!TextUtils.isEmpty(title)) {
//            tvTitle.setText(title);
//        } else {
//            tvTitle.setText("");
//        }
//
//            btnConfirm.setBackgroundResource(R.drawable.btn_normal_selector_bg_blue);
//
//        if (!TextUtils.isEmpty(message)) {
//            tvMessage.setText(message);
//        } else {
//            tvMessage.setText("");
//        }
//        if (!TextUtils.isEmpty(btnText)) {
//            btnConfirm.setText(btnText);
//        }
//        btnConfirm.setOnClickListener(v -> {
//            dialog.dismiss();
//            if (confirm != null) {
//                confirm.onClick(v);
//            }
//        });
//        ivCancel.setOnClickListener(v -> dialog.dismiss());
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setContentView(view);
//        return singleDialog;
//    }
//
//    public void show() {
//        if (dialog != null && !dialog.isShowing()) {
//            dialog.show();
//            WindowManager.LayoutParams wl = dialog.getWindow().getAttributes();
//            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            wl.width = ScreenUtils.getScreenWidth() * 4 / 5;
//            dialog.onWindowAttributesChanged(wl);
//        }
//    }
//
//    public void dismiss() {
//        try {
//            if (dialog != null && dialog.isShowing()) {
//                dialog.cancel();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
