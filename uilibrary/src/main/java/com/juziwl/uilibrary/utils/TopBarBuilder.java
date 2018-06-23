package com.juziwl.uilibrary.utils;

import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.textview.BadgeView;
import com.juziwl.uilibrary.topview.TopSearchView;
import com.wxq.commonlibrary.util.ConvertUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.functions.Consumer;


/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月22日
 * @description 设置并生成topbar
 */
public class TopBarBuilder {
    private View topbar;

    public TopSearchView getTop_search_bar() {
        return top_search_bar;
    }

    private int topBarLayoutId = R.id.top_title_headerbar;

    public TopBarBuilder setCenter_top_title(String text) {
        center_top_title.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        center_top_title.setText(text);
        return this;
    }

    public TopBarBuilder setCenter_down_title(String text) {
        center_down_title.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        center_down_title.setText(text);
        return this;
    }

    private TextView back_btn;
    private TextView complete;
    private TextView left_complete;
    private ImageView image_btn;
    private ImageView back_img;
    private ImageView left_image_btn;
    private RelativeLayout set_setting_black;
    private RelativeLayout title_layout;
    private RelativeLayout centre_layout;
    private RelativeLayout title_right_layout;
    private RelativeLayout right_layout;
    private RelativeLayout left_right_layout;
    private RelativeLayout set_delete;
    private ImageView delete_img;
    private RelativeLayout left_title_right_layout;
    private TextView title_string;
    private TextView contre_textview;
    private TextView right_textview;
    private TextView left_right_textview;
    private TextView center_top_title;
    private TextView center_down_title;
    private TopSearchView top_search_bar;
    private BadgeView rightTitleNewMsg = null, leftRightTitleNewMsg = null;

    public TopBarBuilder(View topbar) {
        this.topbar = topbar;
        back_btn = (TextView) topbar.findViewById(R.id.back_btn);
        complete = (TextView) topbar.findViewById(R.id.complete);
        left_complete = (TextView) topbar.findViewById(R.id.left_complete);
        image_btn = (ImageView) topbar.findViewById(R.id.image_btn);
        back_img = (ImageView) topbar.findViewById(R.id.back_img);
        left_image_btn = (ImageView) topbar.findViewById(R.id.left_image_btn);
        set_setting_black = (RelativeLayout) topbar.findViewById(R.id.set_setting_black);
        title_layout = (RelativeLayout) topbar.findViewById(R.id.title_layout);
        centre_layout = (RelativeLayout) topbar.findViewById(R.id.centre_layout);
        title_right_layout = (RelativeLayout) topbar.findViewById(R.id.title_right_layout);
        right_layout = (RelativeLayout) topbar.findViewById(R.id.right_layout);
        left_right_layout = (RelativeLayout) topbar.findViewById(R.id.left_right_layout);
        set_delete = (RelativeLayout) topbar.findViewById(R.id.set_delete);
        delete_img = (ImageView) topbar.findViewById(R.id.delete_img);
        left_title_right_layout = (RelativeLayout) topbar.findViewById(R.id.left_title_right_layout);
        title_string = (TextView) topbar.findViewById(R.id.title_string);
        contre_textview = (TextView) topbar.findViewById(R.id.contre_textview);
        right_textview = (TextView) topbar.findViewById(R.id.right_textview);
        left_right_textview = (TextView) topbar.findViewById(R.id.left_right_textview);
        center_top_title = (TextView) topbar.findViewById(R.id.center_top_title);
        top_search_bar = (TopSearchView) topbar.findViewById(R.id.top_search_bar);
        center_down_title = (TextView) topbar.findViewById(R.id.center_down_title);

    }

    public TopBarBuilder setLeftImageRes(int resId) {
        set_setting_black.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        back_img.setImageResource(resId);
        return this;
    }

    public TopBarBuilder setDeleteImageRes(int resId) {
        delete_img.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        back_img.setImageResource(resId);
        return this;
    }

    public TopBarBuilder setLeftClickListener(Consumer<Object> onNext) {
        set_setting_black.setVisibility(onNext != null ? View.VISIBLE : View.GONE);
        click(set_setting_black, onNext);
        return this;
    }

    public TopBarBuilder setLeftDeleteClickListener(@DrawableRes int resId, Consumer<Object> onNext) {
        set_delete.setVisibility(onNext != null ? View.VISIBLE : View.GONE);
        delete_img.setImageResource(resId);
        click(set_delete, onNext);
        return this;
    }

    public TopBarBuilder setLeftTitle(String title) {
        set_setting_black.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        back_btn.setBackgroundResource(android.R.color.transparent);
        back_btn.setTextSize(16);
        back_btn.setText(title);
        back_img.setVisibility(View.GONE);
        return this;
    }

    public TopBarBuilder setLeftTitleAndColor(String title, int res) {
        set_setting_black.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        back_btn.setBackgroundResource(android.R.color.transparent);
        back_btn.setTextSize(14);
        back_btn.setText(title);
        back_btn.setTextColor(ContextCompat.getColor(back_btn.getContext(), res));
        back_img.setVisibility(View.GONE);
        return this;
    }

    public TopBarBuilder setLeftTitleSize(int size) {
        back_btn.setTextSize(size);
        return this;
    }

    public TopBarBuilder setTitleBlackBoldStyle(String title, int color) {
        title_layout.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        title_layout.setBackgroundResource(android.R.color.transparent);
        title_string.setText(title);
        title_string.setTextColor(color);
        title_string.setTypeface(Typeface.DEFAULT_BOLD);
        return this;
    }

    public TopBarBuilder setRightHigh(int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
//        params.addRule(width, height);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(0, 0, ConvertUtils.dp2px(10), 0);
        title_right_layout.setLayoutParams(params);
        return this;
    }

    public TopBarBuilder setTitle(String title) {
        title_layout.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        title_string.setGravity(Gravity.CENTER);
        title_string.setText(title);
        return this;
    }

    public TopBarBuilder setTitle(@StringRes int stringId) {
        title_layout.setVisibility(stringId > 0 ? View.VISIBLE : View.GONE);
        title_string.setText(stringId);
        return this;
    }

    public TopBarBuilder setTitleColor(int color) {
        title_string.setTextColor(color);
        return this;
    }

    public TopBarBuilder setTitleColorResId(int color) {
        title_string.setTextColor(ContextCompat.getColor(title_string.getContext(), color));
        return this;
    }


    public TopBarBuilder setTitleAndColor(String title, int color) {
        title_layout.setVisibility(!TextUtils.isEmpty(title) ? View.VISIBLE : View.GONE);
        title_string.setText(title);
        title_string.setTextColor(color);
        return this;
    }

    public TopBarBuilder setTitleLength(int length) {

        title_string.setMaxEms(length);
        return this;
    }

    public TopBarBuilder setRedPointText(String text) {
        centre_layout.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        contre_textview.setText(text);
        return this;
    }

    public TopBarBuilder setRightButtonBackgroundRes(int resId) {
        title_right_layout.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        complete.setBackgroundResource(resId);
        image_btn.setVisibility(View.GONE);
        return this;
    }

    public TopBarBuilder setRightImageRes(int resId) {
        title_right_layout.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        complete.setVisibility(View.GONE);
//        image_btn.setBackgroundResource(resId);
        image_btn.setImageResource(resId);
        return this;
    }

    /**
     * 添加图片并填充左右间距
     *
     * @param resId
     * @param value
     * @return
     */
    public TopBarBuilder setRightImageRes(int resId, float value) {
        title_right_layout.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        complete.setVisibility(View.GONE);
//        image_btn.setBackgroundResource(resId);
        image_btn.setImageResource(resId);
        image_btn.setPadding( ConvertUtils.dp2px(value), 0,  ConvertUtils.dp2px(value), 0);
        return this;
    }


    public TopBarBuilder setRightImageVisible(boolean isvisble) {
        title_right_layout.setVisibility(isvisble ? View.VISIBLE : View.GONE);
        complete.setVisibility(View.GONE);
        title_layout.setVisibility(View.VISIBLE);
        image_btn.setVisibility(isvisble ? View.VISIBLE : View.GONE);
        return this;
    }

    public TopBarBuilder setRightImageMarginRight(float value) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) image_btn.getLayoutParams();
        layoutParams.rightMargin =  ConvertUtils.dp2px(value);
        image_btn.setLayoutParams(layoutParams);
        return this;
    }

    public TopBarBuilder setRightImagePaddingRight(float value) {
        image_btn.setPadding(0, 0,  ConvertUtils.dp2px(value), 0);
        return this;
    }


    // 新添最右边最左边
    public TopBarBuilder setLeftRightImageBackgroundRes(int resId) {
        left_title_right_layout.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        left_complete.setVisibility(View.GONE);
//        image_btn.setBackgroundResource(resId);
        left_image_btn.setImageResource(resId);
        return this;
    }

    public TopBarBuilder setLeftRightImageClickListener(Consumer<Object> onNext) {
//        title_right_layout.setVisibility(listener != null ? View.VISIBLE : View.GONE);
//        image_btn.setOnClickListener(listener);
        click(left_title_right_layout, onNext);
        return this;
    }


    public TopBarBuilder setTitleRightDrawable(int resId, int drawablepadding) {
        title_string.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        title_string.setCompoundDrawablePadding(drawablepadding);
        return this;
    }

    public TopBarBuilder setTitlePaddingLeftRight(int padding) {
        title_string.setPadding(padding, 0, padding, 0);
        return this;
    }

    public TopBarBuilder setTitleClickListener(Consumer<Object> onNext) {
        click(title_layout, onNext);
        return this;
    }

    public TopBarBuilder setRightButtonClickListener(Consumer<Object> onNext) {
        title_right_layout.setVisibility(onNext != null ? View.VISIBLE : View.GONE);
        click(title_right_layout, onNext);
        return this;
    }

    public TopBarBuilder setRightImageClickListener(Consumer<Object> onNext) {
//        title_right_layout.setVisibility(listener != null ? View.VISIBLE : View.GONE);
//        image_btn.setOnClickListener(listener);
        click(title_right_layout, onNext);
        return this;
    }

    public TopBarBuilder setRightText(String text) {
        title_right_layout.setVisibility(!text.isEmpty() ? View.VISIBLE : View.GONE);
        return setRightText(text, 18);
    }

    public TopBarBuilder setRightText(@StringRes int stringId) {
        title_right_layout.setVisibility(stringId > 0 ? View.VISIBLE : View.GONE);
        complete.setBackgroundResource(android.R.color.transparent);
        complete.setText(stringId);
        complete.setTextSize(18);
        return this;
    }

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef({LEFT, RIGHT})
    public @interface Direction {
    }


    public TopBarBuilder setRightTextComponentImage(@DrawableRes int resId, @Direction int direction, int padding) {
        title_right_layout.setVisibility(resId <= 0 ? View.VISIBLE : View.GONE);
        if (direction == LEFT) {
            complete.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        } else {
            complete.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        }
        complete.setCompoundDrawablePadding(padding);
        return this;
    }

    public TopBarBuilder setRightText(String text, float textSize) {
        title_right_layout.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        complete.setBackgroundResource(android.R.color.transparent);
        complete.setText(text);
        complete.setTextSize(textSize);
        return this;
    }

    public TopBarBuilder setRightTextVisable(boolean visable) {
        title_right_layout.setVisibility(visable ? View.VISIBLE : View.GONE);

        return this;
    }

    public TopBarBuilder setRightTextColor(@ColorRes int colorId) {
        title_right_layout.setVisibility(colorId > 0 ? View.VISIBLE : View.GONE);
        complete.setTextColor(complete.getResources().getColor(colorId));
        return this;
    }

    //设置是否可以点击
    public TopBarBuilder setRightEnable(boolean tag) {
        complete.setEnabled(tag);
        complete.setClickable(tag);
        return this;
    }


    public TopBarBuilder setRightTextAndColor(String text, int color) {
        title_right_layout.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        //  complete.setBackgroundResource(R.color.transparent);
        complete.setText(text);
        complete.setTextColor(color);
        return this;
    }

    public TopBarBuilder setRightRedPointText(String text) {
        right_layout.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        right_textview.setText(text);
        return this;
    }

    public TopBarBuilder setRightTextSize(float size) {
        complete.setTextSize(size);
        return this;
    }

    public TopBarBuilder setBackgroundColor(int color) {
        topbar.setBackgroundColor(color);
        return this;
    }

    public View build() {
        return topbar;
    }

    public TextView getTitle_string() {
        return title_string;
    }

    public TextView getRightButton() {
        return complete;
    }

    public ImageView getRightImage() {
        return image_btn;
    }

    public TextView getBack_btn() {
        return back_btn;
    }

    public void setRightLayoutVisible(int visible) {
        title_right_layout.setVisibility(visible);
    }

    public TopBarBuilder isSetMargin(boolean isset) {
        if (!isset) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) topbar.getLayoutParams();
            lp.topMargin = 0;
            topbar.setLayoutParams(lp);
        }
        return this;
    }

    public TopBarBuilder setTopBarBackGround(int color) {
        topbar.setBackgroundResource(color);
        return this;
    }


    public TopBarBuilder setTitleMargin(int left, int right) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) title_layout.getLayoutParams();
        lp.leftMargin = left;
        lp.rightMargin = right;
        title_layout.setLayoutParams(lp);
        return this;
    }

    public TopBarBuilder setRightTextOrImageEnable(boolean enable) {
        title_right_layout.setEnabled(enable);
        return this;
    }

    public TopBarBuilder setRightTextOrImageClickable(boolean enable) {
        title_right_layout.setClickable(enable);
        return this;
    }

    private void click(View v, Consumer<Object> onNext) {
//        RxUtils.click(v, onNext);
    }

    public void setRightNewMsgCount(int count) {
        right_layout.setVisibility(count <= 0 ? View.GONE : View.VISIBLE);
        if (rightTitleNewMsg == null) {
            rightTitleNewMsg = new BadgeView(right_textview.getContext(), right_textview);
            UIUtils.setBadgeStyle(rightTitleNewMsg);
        }
        UIUtils.setUnreadMsgCount(rightTitleNewMsg, count);
    }

    public void setRightNewMsgCountOnParentTopRight(int count) {
        right_layout.setVisibility(count <= 0 ? View.GONE : View.VISIBLE);
        if (rightTitleNewMsg == null) {
            rightTitleNewMsg = new BadgeView(right_textview.getContext(), right_textview);
            UIUtils.setBadgeStyleOnParentTopRight(rightTitleNewMsg);
        }
        UIUtils.setUnreadMsgCount(rightTitleNewMsg, count);
    }

    public void setLeftRightNewMsgCountOnParentTopRight(int count) {
        left_right_layout.setVisibility(count <= 0 ? View.GONE : View.VISIBLE);
        if (leftRightTitleNewMsg == null) {
            leftRightTitleNewMsg = new BadgeView(left_right_textview.getContext(), left_right_textview);
            UIUtils.setBadgeStyleOnParentTopRight(leftRightTitleNewMsg);
        }
        UIUtils.setUnreadMsgCount(leftRightTitleNewMsg, count);
    }

    public RelativeLayout getTitle_right_layout() {
        return title_right_layout;
    }
}
