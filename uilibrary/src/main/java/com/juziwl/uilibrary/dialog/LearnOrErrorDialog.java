package com.juziwl.uilibrary.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.SizeUtils;


/**
 * 创建日期：2018/3/8
 * 描述: 右上角点击点击消失的Dialog  学情报告和错题本
 *
 * @author: wxq
 */
public class LearnOrErrorDialog {

    private XXDialog dialog = null;
    private volatile static LearnOrErrorDialog singleDialog = null;

    private LearnOrErrorDialog() {
    }

    public static LearnOrErrorDialog getInstance() {
        if (singleDialog == null) {
            singleDialog = new LearnOrErrorDialog();
        }
        return singleDialog;
    }


    public LearnOrErrorDialog createDialogAndShow(Context context, String title, String message, int pic, String cancel, ClickListener clickListener) {
        if (dialog != null) {
            dialog.cancelDialog();
        }

        dialog = new XXDialog(context, R.layout.dialog_buy_vip) {
            @Override
            public void convert(DialogViewHolder holder) {
                holder.setText(R.id.tv_content, title);
                holder.setText(R.id.tv_message, message);
                ImageView imageView = holder.getView(R.id.iv_picture);
                imageView.setImageResource(pic);
                holder.setText(R.id.btnCancel, cancel);
                if (cancel.equals("")) {
                    holder.setViewGone(R.id.btnCancel);
                } else {
                    holder.setViewViSible(R.id.btnCancel);

                }

                setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK) {
                            if (clickListener != null) {
                                clickListener.cancel();
                            }
                            dialog.cancelDialog();
                        }
                        return false;
                    }
                });

                //关闭按钮
                holder.getView(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) {
                            clickListener.cancel();
                        }
                        dialog.cancelDialog();
                    }
                });

                holder.getView(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) {
                            clickListener.clickTry();
                        }
                        dialog.cancelDialog();
                    }
                });
                holder.getView(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) {
                            clickListener.clickBuyVip();
                        }
                        dialog.cancelDialog();
                    }
                });
            }
        }.setWidthAndHeight(ScreenUtils.getScreenWidth() * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT).showDialog();

        return singleDialog;
    }



       public LearnOrErrorDialog createDialogAndShow(Context context, String title, String message, int pic, String cancel,String confirm, ClickListener clickListener) {
        if (dialog != null) {
            dialog.cancelDialog();
        }

        dialog = new XXDialog(context, R.layout.dialog_buy_vip) {
            @Override
            public void convert(DialogViewHolder holder) {
                holder.setText(R.id.tv_content, title);
                holder.setText(R.id.tv_message, message);
                ImageView imageView = holder.getView(R.id.iv_picture);
                imageView.setImageResource(pic);
                holder.setText(R.id.btnCancel, cancel);
                holder.setText(R.id.btnConfirm, confirm);
                if (cancel.equals("")) {
                    holder.setViewGone(R.id.btnCancel);
                } else {
                    holder.setViewViSible(R.id.btnCancel);

                }

                setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK) {
                            if (clickListener != null) {
                                clickListener.cancel();
                            }
                            dialog.cancelDialog();
                        }
                        return false;
                    }
                });

                //关闭按钮
                holder.getView(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) {
                            clickListener.cancel();
                        }
                        dialog.cancelDialog();
                    }
                });

                holder.getView(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) {
                            clickListener.clickTry();
                        }
                        dialog.cancelDialog();
                    }
                });
                holder.getView(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) {
                            clickListener.clickBuyVip();
                        }
                        dialog.cancelDialog();
                    }
                });
            }
        }.setWidthAndHeight(ScreenUtils.getScreenWidth() * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT).showDialog();

        return singleDialog;
    }







    public void show() {
//        if (dialog != null && !dialog.isShowing()) {
//            dialog.show();
//            WindowManager.LayoutParams wl = dialog.getWindow().getAttributes();
//            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            wl.width = DisplayUtils.getScreenWidth() * 4 / 5;
//            dialog.onWindowAttributesChanged(wl);
//        }
    }

    public void dismiss() {
//        try {
//            if (dialog != null && dialog.isShowing()) {
//                dialog.cancel();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public interface ClickListener {

        void clickBuyVip();

        void clickTry();

        void cancel();

    }


}
