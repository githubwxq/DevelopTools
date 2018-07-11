//package com.juziwl.uilibrary.utils;
//
//import android.graphics.Color;
//import android.support.v4.content.ContextCompat;
//import android.view.View;
//
//import com.juziwl.uilibrary.R;
//import com.juziwl.uilibrary.textview.BadgeView;
//import com.wxq.commonlibrary.util.ConvertUtils;
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2017/10/28
// * @description 公共的对控件的操作
// */
//public class UIUtils {
//
//    public static void setUnreadMsgCount(BadgeView badgeView, long count) {
//        if (count == 0) {
//            badgeView.setText(String.valueOf(count));
//            badgeView.setBackgroundResource(R.mipmap.common_redpoint);
//            badgeView.setVisibility(View.GONE);
//        } else if (count <= 99) {
//            badgeView.setVisibility(View.VISIBLE);
//            badgeView.setText(String.valueOf(count));
//            badgeView.setBackgroundDrawable(null);
//            badgeView.show();
//        } else {
//            badgeView.setText(null);
//            badgeView.setVisibility(View.VISIBLE);
//            badgeView.setBackgroundResource(R.mipmap.icon_max_msgcount);
//            badgeView.show();
//        }
//    }
//
//    public static void setBadgeStyle(BadgeView badgeView) {
//        // 显示的位置.中间，还有其他位置属性
//        badgeView.setBadgePosition(BadgeView.POSITION_CENTER);
//        // 文本颜色
//        badgeView.setTextColor(Color.WHITE);
//        // 背景颜色
//        badgeView.setBadgeBackgroundColor(ContextCompat.getColor(badgeView.getContext(), R.color.color_ff6f26));
//        // 文本大小
//        badgeView.setTextSize(10);
//        badgeView.setBadgeMargin(0, 0);
//        badgeView.setPadding(ConvertUtils.dp2px(3), 0, ConvertUtils.dp2px(3), 0);
//    }
//
//    public static void setBadgeStyleOnParentTopRight(BadgeView badgeView) {
//        // 显示的位置.中间，还有其他位置属性
//        badgeView.setBadgePosition(BadgeView.POSITION_CENTER);
////        if (Global.loginType == Global.PARENT) {
//            // 文本颜色
//            badgeView.setTextColor(ContextCompat.getColor(badgeView.getContext(), R.color.color_ff6f26));
//            // 背景颜色
//            badgeView.setBadgeBackgroundColor(Color.WHITE);
////        } else {
//            // 文本颜色
//            badgeView.setTextColor(Color.WHITE);
//            // 背景颜色
//            badgeView.setBadgeBackgroundColor(ContextCompat.getColor(badgeView.getContext(), R.color.color_ff6f26));
////        }
//        // 文本大小
//        badgeView.setTextSize(12);
//        badgeView.setBadgeMargin(0, 0);
//    }
//
//    public static void setBadgeMsgOnParentTopRight(BadgeView badgeView) {
//        // 显示的位置.中间，还有其他位置属性
//        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        // 文本颜色
//        badgeView.setTextColor(ContextCompat.getColor(badgeView.getContext(), R.color.color_ff6f26));
//        // 背景颜色
//        badgeView.setBadgeBackgroundColor(Color.WHITE);
//        // 文本大小
//        badgeView.setTextSize(12);
//        badgeView.setBadgeMargin(0, 0);
//    }
//}
