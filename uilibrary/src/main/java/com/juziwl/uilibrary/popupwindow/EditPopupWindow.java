package com.juziwl.uilibrary.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.edittext.EmojiFilterEditText;
import com.juziwl.uilibrary.tools.UiUtils;
import com.wxq.commonlibrary.util.KeyboardUtils;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.SizeUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;

public class EditPopupWindow extends BasePopup<EditPopupWindow> {

    private OnViewListener mOnViewListener;

    private Activity activity;


    public EditPopupWindow(Activity context) {
         activity=context;
         setContentView(context, R.layout.layout_circle_edit, ScreenUtils.getScreenWidth(), SizeUtils.dp2px(45))
                .setAnimationStyle(R.style.BottomPopAnim)
                .setFocusAndOutsideEnable(true).apply();
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void initViews(View view, EditPopupWindow popup) {
        if (mOnViewListener != null) {
            mOnViewListener.initViews(view, popup);
        }
      EmojiFilterEditText emojiFilterEditText= view.findViewById(R.id.input);
      Button btn_send= view.findViewById(R.id.btn_send);
      LinearLayout ll_main= view.findViewById(R.id.ll_main);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(emojiFilterEditText.getText().toString())){
                    ToastUtils.showShort("请输入内容");
                }else {
                    if (mOnViewListener != null) {
                        mOnViewListener.getEditString(emojiFilterEditText.getText().toString());
                        dismiss();
                    }
                }
            }
        });
        popup.setOnDismissListener(() -> UIHandler.getInstance().postDelayed(() -> KeyboardUtils.hideSoftInput(activity),100));
    }
    public EditPopupWindow setOnViewListener(OnViewListener listener) {
        this.mOnViewListener = listener;
        return this;
    }

    public interface OnViewListener {
        void initViews(View view, EditPopupWindow popup);
        void getEditString(String text);
    }

    public EditPopupWindow showPopup(Activity activity){
        KeyboardUtils.showSoftInput(activity);
        setNeedReMeasureWH(true);
        showAtLocation(UiUtils.getRootView(activity), Gravity.BOTTOM, 0, 0);
        return this;
    }
}
