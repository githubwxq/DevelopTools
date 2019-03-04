package com.juziwl.uilibrary.edittext;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EdgeEffect;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/17
 * desc:edittext封装提供验证
 * version:1.0
 */
public class SuperEditText extends android.support.v7.widget.AppCompatEditText {

    /**
     * 确保嵌套在scrowview中可以滑动。
     */
    private boolean scrollble=true;

    /**
     * 电子邮箱的正则表达式。
     */
    public static final String REGEX_EMAIL = ".+@.+\\.[a-z]+";


    private void init(Context context) {
//        、、去除横线
        setBackground(null);
    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父控件不拦截事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
    public SuperEditText(Context context) {
        super(context);
        init(context);
    }


    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    /**
     * 过滤掉常见特殊字符,常见的表情
     */
    public  void setEtFilterAndLength(int number) {
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        //过滤出字母数字中文
        InputFilter otherFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                return source.toString().replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
            }
        };
       setFilters(new InputFilter[]{specialCharFilter, otherFilter, new InputFilter.LengthFilter(number)});
    }



    /**
     * 其他的filter 不能输入空格 可以添加使用
     */
    class SpaceFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            return " ".equals(source.toString()) ? "" : source;
        }
    }


    /**
     * 判断手机号是否输入正确
     */
    public static boolean isMobileNumber(String mobiles) {
        String telRegex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }


    /**
     * 判断是否满足邮箱规则
     * @param str   字符串
     * @return true/false
     */
    public static boolean isEmail(String str) {
        return str != null && str.length() > 0 && str.matches(REGEX_EMAIL);
    }


    /**
     * 设置手机输入样式
     */
    public  void setPhoneStyle() {
        setInputType(EditorInfo.TYPE_CLASS_PHONE);
        setSingleLine();
        setEtFilterAndLength(11);
        //只能输入对应的
        //setKeyListener(DigitsKeyListener.getInstance("0123456789"));
    }

    /**
     * 设置密码样式
     */
    public  void setPassWordStyle(boolean showPassWord) {
        if (showPassWord) {
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }






}


//设置搜索  xml 中 android:imeOptions="actionSearch"

//editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
//        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//        input.setOnEditorActionListener((v, actionId, event) -> {
//         if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//         if (input.getText().length() >= 6 && input.getText().length() <= GlobalContent.MAX_PHONE_LENGTH) {
//         Bundle bundle = new Bundle();
//         bundle.putString(AddContactsActivity.EXTRA_PHONE, input.getText().toString());
//         interactiveListener.onInteractive(AddContactsActivity.ACTION_SEARCH, bundle);
//         } else {
//         ToastUtils.showToast(R.string.incorrect_phone_or_exueno);
//         }
//         return true;
//         }
//         return false;
//         });

//、、代码设置只能输入数字setInputType(EditorInfo.TYPE_CLASS_PHONE);
//android:digits 属性， 这种方式可以指出要支持的字符android:digits="0123456789abcdefghijklmnopqrstuvwxyz"

//以下代码提供给activity使用 点击非edittext区域隐藏
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
//            if (isShouldHideInput(v, ev)) {
////                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////                if (imm != null) {
////                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
////                    //处理Editext的光标隐藏、显示逻辑
////                    etSuperedit.clearFocus();
////                }
//
//                KeyboardUtils.hideSoftInput(v);
//
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }
//
//    public boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = {0, 0};
//            //获取输入框当前的location位置
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0];
//            int top = leftTop[1];
//            int bottom = top + v.getHeight();
//            int right = left + v.getWidth();
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }





//软键盘弹出顶起布局的小技巧
//首先我想到了Activity的windowSoftInputMode属性，这个属性能影响两件事情：
//        1、当有焦点产生时，软键盘是隐藏还是显示
//        2、是否改变活动主窗口大小以便腾出空间展示软键盘
//        它有以下值可以设置：
//        1、stateUnspecified：软键盘的状态并没有指定，系统将选择一个合适的状态或依赖于主题的设置
//        2、stateUnchanged：当这个activity出现时，软键盘将一直保持在上一个activity里的状态，无论是隐藏还是显示
//        3、stateHidden：用户选择activity时，软键盘总是被隐藏
//        4、stateAlwaysHidden：当该Activity主窗口获取焦点时，软键盘也总是被隐藏的
//        5、stateVisible：软键盘通常是可见的
//        6、stateAlwaysVisible：用户选择activity时，软键盘总是显示的状态
//        7、adjustUnspecified：默认设置，通常由系统自行决定是隐藏还是显示
//        8、adjustResize：该Activity总是调整屏幕的大小以便留出软键盘的空间
//        9、adjustPan：当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖，并且用户总能看到输入内容的部分
//        可以设置一个或多个，多个之间用|分开。这些值中符合我们要求的是adjustResize和adjustPan。先看adjustPan，它会将当前获取了焦点的EditText之上的布局整体上移，以此来达到EditText不被软键盘覆盖的目的。但如果我只想让EditText和与EditText有关的一些控件上移，而它们之上的控件保持不变呢?OK，这时候我们就需要用到adjustResize，但是光用它还是不够的，还需要我们的布局配合。
//        ---------------------
//        作者：康小白Code
//        来源：CSDN
//        原文：https://blog.csdn.net/k_bb_666/article/details/78544738?utm_source=copy
//        版权声明：本文为博主原创文章，转载请附上博文链接！