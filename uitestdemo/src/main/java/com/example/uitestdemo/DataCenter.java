package com.example.uitestdemo;

import com.example.uitestdemo.bean.ItemBean;
import com.example.uitestdemo.fragment.components.banner.BannerFragment;
import com.example.uitestdemo.fragment.components.button.ButtonStyleFragment;
import com.example.uitestdemo.fragment.components.button.ShadowWeightFragment;
import com.example.uitestdemo.fragment.components.dialog.PayDialogFragment;
import com.example.uitestdemo.fragment.components.edit.VerifyCodeEditTextFragment;
import com.example.uitestdemo.fragment.components.flowlayout.FlowLayoutFragment;
import com.example.uitestdemo.fragment.components.flowview.FlowViewFragment;
import com.example.uitestdemo.fragment.components.popupwindow.EasyPopupFragment;
import com.example.uitestdemo.fragment.components.scrollingmarquee.MarqueeViewFragment;

import java.util.ArrayList;
import java.util.List;

public class DataCenter {


    public static List<String> componentList = new ArrayList<>();
    public static List<String> utilsList = new ArrayList<>();
    public static List<String> expandList = new ArrayList<>();


    static {
        componentList.add("轮播条");
        componentList.add("按钮");
        componentList.add("popupwindow");
        componentList.add("文字滚动");
        componentList.add("Dialog弹框");
        componentList.add("输入框");
        componentList.add("流布局");
        componentList.add("图片处理");
        componentList.add("悬浮框");

//        componentList.add("引导页");
//        componentList.add("图片处理");
//        componentList.add("通用布局");
//        componentList.add("文字滚动");
//        componentList.add("选择器");
//        componentList.add("弹出框");
//        componentList.add("进度条");
//        componentList.add("列表刷新");
//        componentList.add("搜索框");
//        componentList.add("下拉框");
//
//
//        componentList.add("对话框");
//        componentList.add("输入框");
//        componentList.add("流布局");
//        componentList.add("引导页");
//        componentList.add("图片处理");
//        componentList.add("通用布局");
//        componentList.add("文字滚动");
//        componentList.add("选择器");
//        componentList.add("弹出框");
//        componentList.add("进度条");
//        componentList.add("列表刷新");
//        componentList.add("搜索框");
//        componentList.add("下拉框");


    }

    public static List<ItemBean> getItemWithName(String name) {

        if ("轮播条".equals(name)) {
            List<ItemBean> lunbo = new ArrayList<>();
            lunbo.add(new ItemBean("viewpage实现轮播", BannerFragment.newInstance()));
            return lunbo;
        }
        if ("按钮".equals(name)) {
            List<ItemBean> guide = new ArrayList<>();
            guide.add(new ItemBean("倒计时按钮", ButtonStyleFragment.newInstance()));
            guide.add(new ItemBean("阴影按钮文本", ShadowWeightFragment.newInstance()));
            return guide;
        }

        if ("popupwindow".equals(name)) {
            List<ItemBean> guide = new ArrayList<>();
            guide.add(new ItemBean("easypopup", EasyPopupFragment.newInstance()));

            return guide;
        }

        if ("文字滚动".equals(name)) {
            List<ItemBean> guide = new ArrayList<>();
            guide.add(new ItemBean("文字滚动", MarqueeViewFragment.newInstance()));
            return guide;
        }


        if ("Dialog弹框".equals(name)) {
            List<ItemBean> button = new ArrayList<>();
            button.add(new ItemBean("支付dialog", PayDialogFragment.newInstance()));
            return button;
        }
        if ("流布局".equals(name)) {
            List<ItemBean> guide = new ArrayList<>();
            guide.add(new ItemBean("各种流布局", FlowLayoutFragment.newInstance()));
            return guide;
        }

        if ("输入框".equals(name)) {
            List<ItemBean> button = new ArrayList<>();
            button.add(new ItemBean("验证码输入框", VerifyCodeEditTextFragment.newInstance()));
            return button;
        }
        if ("悬浮框".equals(name)) {
            List<ItemBean> button = new ArrayList<>();
            button.add(new ItemBean("悬浮框", FlowViewFragment.newInstance()));
            return button;
        }

        if ("图片处理".equals(name)) {
            List<ItemBean> button = new ArrayList<>();
            button.add(new ItemBean("图片处理", VerifyCodeEditTextFragment.newInstance()));
            return button;
        }



        return null;
    }


}
