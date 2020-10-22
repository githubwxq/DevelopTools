package com.juziwl.uilibrary.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.utils.ScreenUtils;

import java.util.ArrayList;


/**
 * @author Army
 * @version V_5.0.0
 * @modify Neil
 * @date 2016/4/27
 * @description 支付面板
 */
public class PayDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView into_count_title_top, into_count, into_count_title;
    private LinearLayout into_count_text_layout;
    private RelativeLayout number_black_layout;
    private RelativeLayout number_1_layout, number_2_layout, number_3_layout,
            number_4_layout, number_5_layout, number_6_layout, number_7_layout,
            number_8_layout, number_9_layout, number_0_layout,
            number_delete_layout;
    private int count = 0;
    private ImageView number_point_1, number_point_2, number_point_3, number_point_4, number_point_5, number_point_6;
    private ImageView ezhifu_cancel;
    private ArrayList<ImageView> imageViewList;
    private StringBuilder passwordStr;
    private String title = "", money = "", desc = "";
    private static final int NUMBER_6 = 6;

    /**
     * 忘记密码
     */
    private TextView tv_forget_password;
    private ClickPassword clickPassword;

    public void setClickPassword(ClickPassword clickPassword) {
        this.clickPassword = clickPassword;
    }

    public PayDialog(Context context, String title, String desc, String money) {
        super(context, R.style.common_chooseDialog);
        this.context = context;
        this.title = title;
        this.money = money;
        this.desc = desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View popMean = View.inflate(context, R.layout.common_account_number_paypassword, null);
        into_count_text_layout = (LinearLayout) popMean.findViewById(R.id.into_count_text_layout);
        into_count = (TextView) popMean.findViewById(R.id.into_count);
        tv_forget_password = (TextView) popMean.findViewById(R.id.tv_forget_password);
        into_count_title_top = (TextView) popMean.findViewById(R.id.into_count_title_top);
        number_black_layout = (RelativeLayout) popMean.findViewById(R.id.number_black_layout);
        number_1_layout = (RelativeLayout) popMean.findViewById(R.id.number_1_layout);
        number_2_layout = (RelativeLayout) popMean.findViewById(R.id.number_2_layout);
        number_3_layout = (RelativeLayout) popMean.findViewById(R.id.number_3_layout);
        number_4_layout = (RelativeLayout) popMean.findViewById(R.id.number_4_layout);
        number_5_layout = (RelativeLayout) popMean.findViewById(R.id.number_5_layout);
        number_6_layout = (RelativeLayout) popMean.findViewById(R.id.number_6_layout);
        number_7_layout = (RelativeLayout) popMean.findViewById(R.id.number_7_layout);
        number_8_layout = (RelativeLayout) popMean.findViewById(R.id.number_8_layout);
        number_9_layout = (RelativeLayout) popMean.findViewById(R.id.number_9_layout);
        number_0_layout = (RelativeLayout) popMean.findViewById(R.id.number_0_layout);
        into_count_title = (TextView) popMean.findViewById(R.id.into_count_title);
        ezhifu_cancel = (ImageView) popMean.findViewById(R.id.ezhifu_cancel);

        number_delete_layout = (RelativeLayout) popMean.findViewById(R.id.number_delete_layout);

        number_point_1 = (ImageView) popMean.findViewById(R.id.number_point_1);
        number_point_2 = (ImageView) popMean.findViewById(R.id.number_point_2);
        number_point_3 = (ImageView) popMean.findViewById(R.id.number_point_3);
        number_point_4 = (ImageView) popMean.findViewById(R.id.number_point_4);
        number_point_5 = (ImageView) popMean.findViewById(R.id.number_point_5);
        number_point_6 = (ImageView) popMean.findViewById(R.id.number_point_6);
        imageViewList = new ArrayList<>();
        passwordStr = new StringBuilder();
        imageViewList.add(number_point_1);
        imageViewList.add(number_point_2);
        imageViewList.add(number_point_3);
        imageViewList.add(number_point_4);
        imageViewList.add(number_point_5);
        imageViewList.add(number_point_6);

        number_1_layout.setOnClickListener(this);
        number_2_layout.setOnClickListener(this);
        number_3_layout.setOnClickListener(this);
        number_4_layout.setOnClickListener(this);
        number_5_layout.setOnClickListener(this);
        number_6_layout.setOnClickListener(this);
        number_7_layout.setOnClickListener(this);
        number_8_layout.setOnClickListener(this);
        number_9_layout.setOnClickListener(this);
        number_0_layout.setOnClickListener(this);
        number_delete_layout.setOnClickListener(this);
        number_black_layout.setOnClickListener(this);
        ezhifu_cancel.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);

        into_count_title_top.setText(title);

        if (!TextUtils.isEmpty(money)) {
            setMoneyLayout(desc, money);
        }

        setContentView(popMean);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ScreenUtils.getScreenWidth(context);
        window.setAttributes(lp);
        if (viewInitFinish != null) {
            viewInitFinish.initFinish(tv_forget_password);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ezhifu_cancel) {
            count = 0;
            imageViewList.clear();
            imageViewList = null;
            passwordStr = null;
            dismiss();
        } else if (id == R.id.number_1_layout) {
            numberInput("1");
        } else if (id == R.id.number_2_layout) {
            numberInput("2");
        } else if (id == R.id.number_3_layout) {
            numberInput("3");
        } else if (id == R.id.number_4_layout) {
            numberInput("4");
        } else if (id == R.id.number_5_layout) {
            numberInput("5");
        } else if (id == R.id.number_6_layout) {
            numberInput("6");
        } else if (id == R.id.number_7_layout) {
            numberInput("7");
        } else if (id == R.id.number_8_layout) {
            numberInput("8");
        } else if (id == R.id.number_9_layout) {
            numberInput("9");
        } else if (id == R.id.number_0_layout) {
            numberInput("0");
        } else if (id == R.id.number_delete_layout) {
            deleteInput();
        } else if (id == R.id.tv_forget_password) {
            if (clickPassword != null) {
                clickPassword.onClick(tv_forget_password);
            }
        }

    }

    private void numberInput(String number) {
        try {
            if (count == NUMBER_6) {
                return;
            }
            count++;
            imageViewList.get(count - 1).setVisibility(View.VISIBLE);
            passwordStr.append(number);
            if (count == NUMBER_6) {
                listener.getStrListener(getpasswordStr());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @SuppressLint("NewApi")
    private void deleteInput() {
        if (count == 0) {
            return;
        }
        imageViewList.get(count - 1).setVisibility(View.INVISIBLE);
        passwordStr = passwordStr.deleteCharAt(count - 1);
        count--;

    }

    public String getpasswordStr() {
        return passwordStr.toString();
    }

    public void passwordError() {
        for (int i = 0; i < imageViewList.size(); i++) {
            count = 0;
//			passwordStr.delete(0, passwordStr.length()-1);
            imageViewList.get(i).setVisibility(View.INVISIBLE);
        }
        passwordStr = new StringBuilder("");
    }

    public void setPasswordTitle(CharSequence title) {
        into_count_title_top.setText(title);
    }

    public void setMoneyLayout(String desc, CharSequence money) {
        into_count_text_layout.setVisibility(View.VISIBLE);
        into_count.setText(money);
        into_count_title.setText(desc);
    }

    public interface GetStrListener {
        /**
         * 获取str监听
         */
        void getStrListener(String  password);
    }

    GetStrListener listener;


    public interface ClickPassword {
        void onClick(TextView forgetNumber);
    }

    public void setGetStrListener(GetStrListener listenr) {
        listener = listenr;
    }


    public ViewInitFinish getViewInitFinish() {
        return viewInitFinish;
    }

    public void setViewInitFinish(ViewInitFinish viewInitFinish) {
        this.viewInitFinish = viewInitFinish;
    }

    ViewInitFinish viewInitFinish;
    //获取忘记密码的回调

    public interface ViewInitFinish {
        void initFinish(TextView forgetNumber);
    }


}
