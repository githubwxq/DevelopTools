package com.juziwl.uilibrary.dialog;

import android.content.Context;
import android.view.View;

import com.juziwl.uilibrary.R;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/3/5
 * @description 动态和作业删除的dialog
 */
public class DeleteDialog {

    public static void showDeleteDialog(Context context, final String title, final View.OnClickListener onDeleteClickListener) {
        XXDialog dialog = new XXDialog(context, R.layout.layout_delete_dialog) {
            @Override
            public void convert(DialogViewHolder holder) {
                holder.setText(R.id.title, title);
                holder.setOnClick(R.id.tv_delete, v -> {
                    cancelDialog();
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onClick(v);
                    }
                });
                holder.setOnClick(R.id.tv_cancel, v -> cancelDialog());
            }
        };
        dialog.fromBottom().fullWidth().setCancelAble(true).setCanceledOnTouchOutside(true).showDialog();
    }
}
