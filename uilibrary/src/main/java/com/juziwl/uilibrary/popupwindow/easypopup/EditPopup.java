package com.juziwl.uilibrary.popupwindow.easypopup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.SizeUtils;

public class EditPopup extends BasePopup<EditPopup> {
    private static final String TAG = "EditPopup";

    private EditText editText;
    private Button mCancelBtn;

    public static EditPopup create(Context context){
        return new EditPopup(context);
    }

    protected EditPopup(Context context) {
        setContext(context);
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void initAttributes() {
        setContentView(R.layout.edit_popup_window, ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(40));
        setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f);
		//setXxx() 方法
        // 设置弹出窗体需要软键盘
        getmPopupWindow().setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        getmPopupWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    @Override
    protected void initViews(View view, EditPopup popup) {
        editText = findViewById(R.id.edit);
        editText.setFocusable(true);
    }
}