package com.juziwl.uilibrary.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


import androidx.appcompat.widget.AppCompatEditText;

import com.juziwl.uilibrary.R;


/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/08/22
 * @description 带删除图标的编辑框
 */
public class DeletableEditText extends AppCompatEditText {

    private Drawable mRightDrawable;

    private boolean isPassword = false;
    private boolean isPasswordVisible = false;
    /**
     * 点击图片的范围扩大
     */
    private int padding = 0;

    public DeletableEditText(Context context) {
        this(context, null);
        initView();
    }

    public DeletableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        padding = (int) (getResources().getDisplayMetrics().density * 4 + 0.5f);
        //上下左右图片的位置  0 :left, 1: top,2 :right, and 3 :bottom
        Drawable[] drawables = this.getCompoundDrawables();
        //取得right位置的Drawable
        //即布局文件中设置的android:drawableRight
        mRightDrawable = drawables[2];
        //设置EditText文字变化的监听
        addTextChangedListener(new TextWatcherImpl());

        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (!isPassword) {
                    //初始化时让右边clean图标不可见
                    boolean isVisible = getText().toString().length() >= 1;
                    setClearDrawableVisible(isVisible);
                }


            } else {
                if (!isPassword) {
                    setClearDrawableVisible(false);
                }
            }

            if (editListener != null) {
                editListener.foucus(hasFocus);
            }

        });
        isPassword = isPassword(getInputType());
        //专门为是密码又有×提供的tag设置的特殊标记
        String special = (String) getTag();
        if (isPassword(getInputType())) {
            if (("special").equals(special)) {
                isPassword = false;
            } else {
                isPassword = true;
            }
        } else {
            isPassword = false;
        }
        if (!isPassword) {
            //初始化时让右边clean图标不可见
            setClearDrawableVisible(false);
        }
    }

    /**
     * 隐藏或者显示右边clean的图标
     *
     * @param isVisible
     */
    public void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        //设置该控件left, top, right, and bottom处的图标
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight() - padding)
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    if (isPassword) {
                        if (isPasswordVisible) {
                            //隐藏密码
                            setTransformationMethod(PasswordTransformationMethod.getInstance());
                            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_edittext_pwd_close, 0);
                        } else {
                            //显示密码
                            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_edittext_pwd_show, 0);
                        }
                        isPasswordVisible = !isPasswordVisible;
                    } else {
                        this.setText("");
                        if (editListener != null) {
                            editListener.clickDelete();
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当输入结束后判断是否显示右边clean的图标
     */
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = getText().toString().length() >= 1;
            if (!isPassword && hasFocus()) {
                setClearDrawableVisible(isVisible);
            }
        }
    }

    private boolean isPassword(int inputType) {
        final int variation =
                inputType & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_MASK_VARIATION);
        return variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);
    }

    public EditListener getEditListtener() {
        return editListener;
    }

    public void setEditListtener(EditListener editListtener) {
        this.editListener = editListtener;
    }


    EditListener editListener;

    public interface EditListener {

        void clickDelete();

        void foucus(boolean isFoucus);

    }


}
