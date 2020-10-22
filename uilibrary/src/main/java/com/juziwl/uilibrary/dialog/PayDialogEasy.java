package com.juziwl.uilibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.edittext.PayPwdEditText;
import com.juziwl.uilibrary.utils.ScreenUtils;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author xyx on 2016/11/17 16:09
 * @e-mail 384744573@qq.com
 * @see [相关类/方法](可选)
 */

public class PayDialogEasy extends Dialog implements View.OnClickListener {

    private Context mContext;
    private LinearLayout contentView;
    private LinearLayout ll_close;
    private PayPwdEditText payPwdEditText;
    private TextView tv_money;
    private TextView tv_text[];
    private ImageView iv_delete;
    private TextView tvFindPassword;

    private String pinCode = "";

    public PayDialogEasy(final Context context) {
        super(context, R.style.common_dialog);
        mContext = context;
        contentView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_my_pay, null);
        setContentView(contentView);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setLayout(ScreenUtils.getScreenWidth(mContext), ViewGroup.LayoutParams.WRAP_CONTENT); //设置宽高,这样设置才有效
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        tv_money = (TextView) contentView.findViewById(R.id.tv_money);
        ll_close = (LinearLayout) contentView.findViewById(R.id.ll_close);
        payPwdEditText = (PayPwdEditText) contentView.findViewById(R.id.ppe_pwd);
        tvFindPassword = contentView.findViewById(R.id.tv_find_password);
        tv_text = new TextView[10];
        tv_text[0] = (TextView) contentView.findViewById(R.id.tv_text_0);
        tv_text[1] = (TextView) contentView.findViewById(R.id.tv_text_1);
        tv_text[2] = (TextView) contentView.findViewById(R.id.tv_text_2);
        tv_text[3] = (TextView) contentView.findViewById(R.id.tv_text_3);
        tv_text[4] = (TextView) contentView.findViewById(R.id.tv_text_4);
        tv_text[5] = (TextView) contentView.findViewById(R.id.tv_text_5);
        tv_text[6] = (TextView) contentView.findViewById(R.id.tv_text_6);
        tv_text[7] = (TextView) contentView.findViewById(R.id.tv_text_7);
        tv_text[8] = (TextView) contentView.findViewById(R.id.tv_text_8);
        tv_text[9] = (TextView) contentView.findViewById(R.id.tv_text_9);
        iv_delete = (ImageView) contentView.findViewById(R.id.iv_text_delete);

        payPwdEditText.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color_999, R.color.color_F2F2F2, 30);

        initEvent();
    }

    private void initEvent() {
        ll_close.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        for (int i = 0; i < tv_text.length; i++) {
            tv_text[i].setOnClickListener(this);
        }
        tvFindPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_close) {
            dismiss();
        } else if (id == R.id.iv_text_delete) {
            setText(-1);
        } else if (id == R.id.tv_text_0) {
            setText(0);
        } else if (id == R.id.tv_text_1) {
            setText(1);
        } else if (id == R.id.tv_text_2) {
            setText(2);
        } else if (id == R.id.tv_text_3) {
            setText(3);
        } else if (id == R.id.tv_text_4) {
            setText(4);
        } else if (id == R.id.tv_text_5) {
            setText(5);
        } else if (id == R.id.tv_text_6) {
            setText(6);
        } else if (id == R.id.tv_text_7) {
            setText(7);
        } else if (id == R.id.tv_text_8) {
            setText(8);
        } else if (id == R.id.tv_text_9) {
            setText(9);
        } else if (id == R.id.tv_find_password) {//                ForgetPassWordUtils.jumpSetPayPwdActivity(mContext);
            dismiss();
        }
    }

    private void setText(int i) {
        String text = payPwdEditText.getEditText().getText().toString();
        if (i < 0) {
            if (!TextUtils.isEmpty(text)) {
                text = text.substring(0, text.length() - 1);
            } else {
                return;
            }
        } else {
            text = text.concat(String.valueOf(i));
        }
        payPwdEditText.getEditText().setText(text);
        payPwdEditText.afterTextChanged();

    }


    /**
     * 设置money
     *
     * @param money
     */
    public void setMoney(float money) {
        tv_money.setText(String.format("￥%.2f", money));
    }
    public void setMoney(String money) {
        tv_money.setText(money);
    }

    public PayPwdEditText getPayPwdEditText() {
        return payPwdEditText;
    }

    @Override
    public void dismiss() {
        resetPinCode();
        super.dismiss();
    }

    /**
     * 重置Pin code输入框
     */
    private void resetPinCode() {
        payPwdEditText.clearText();
    }
}
