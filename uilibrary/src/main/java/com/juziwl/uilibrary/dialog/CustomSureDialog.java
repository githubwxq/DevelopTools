package com.juziwl.uilibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.juziwl.uilibrary.R;


/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月25日
 * @description 确定dialog
 */
public class CustomSureDialog {
    private static CustomSureDialog instance;
    private Dialog alertDialog;

    private CustomSureDialog(){

    }

    public static CustomSureDialog getInstance() {
        if (instance == null) {
            instance = new CustomSureDialog();
        }
        return instance;
    }

    public Dialog createAlertDialog(Context context, String content, String btncontent, View.OnClickListener listener) {
        // 创建自定义样式dialog
        alertDialog = new Dialog(context, R.style.common_textDialogStyle);
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            // 得到加载view
            View v = inflater.inflate(R.layout.common_layout_sure_dialog, null);
            TextView tv_content = (TextView) v.findViewById(R.id.content);
            Button btnSure = (Button) v.findViewById(R.id.btnSure);
            tv_content.setText(content);
            btnSure.setText(btncontent);

            btnSure.setOnClickListener(listener);

            alertDialog.setCanceledOnTouchOutside(false);
            // 不可以用“返回键”取消
            alertDialog.setCancelable(false);
            // 设置布局
            alertDialog.setContentView(v);
        }catch (Exception e){
            e.printStackTrace();
        }
        return alertDialog;
    }

    public void cancelAlertDialog() {
        if (alertDialog != null) {
            alertDialog.cancel();
        }
    }

    public boolean isAlertDialog() {
        boolean flag;
        flag = alertDialog != null && alertDialog.isShowing();
        return flag;
    }

}
