package com.example.uitestdemo;

import androidx.fragment.app.Fragment;

import com.example.uitestdemo.bean.ItemBean;
import com.example.uitestdemo.fragment.SuperTextViewFragment;
import com.example.uitestdemo.fragment.TestDispatchEventFragment;
import com.example.uitestdemo.fragment.TestMemoryFragment;
import com.example.uitestdemo.fragment.TextViewFragment;
import com.example.uitestdemo.fragment.VlayoutFragment;
import com.example.uitestdemo.fragment.components.banner.BannerFragment;
import com.example.uitestdemo.fragment.components.button.ButtonStyleFragment;
import com.example.uitestdemo.fragment.components.button.ShadowWeightFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DataCenter {


    public static List<String> componentList = new ArrayList<>();
    public static List<String> utilsList = new ArrayList<>();
    public static List<String> expandList = new ArrayList<>();


    static {
        componentList.add("轮播条");
        componentList.add("按钮");
        componentList.add("对话框");
        componentList.add("输入框");
        componentList.add("流布局");
        componentList.add("引导页");
        componentList.add("图片处理");
        componentList.add("通用布局");
        componentList.add("文字滚动");
        componentList.add("选择器");
        componentList.add("弹出框");
        componentList.add("进度条");
        componentList.add("列表刷新");
        componentList.add("搜索框");
        componentList.add("下拉框");


        componentList.add("对话框");
        componentList.add("输入框");
        componentList.add("流布局");
        componentList.add("引导页");
        componentList.add("图片处理");
        componentList.add("通用布局");
        componentList.add("文字滚动");
        componentList.add("选择器");
        componentList.add("弹出框");
        componentList.add("进度条");
        componentList.add("列表刷新");
        componentList.add("搜索框");
        componentList.add("下拉框");


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
        if ("对话框".equals(name)) {
            List<ItemBean> button = new ArrayList<>();
            button.add(new ItemBean("按钮一", SuperTextViewFragment.newInstance()));
            button.add(new ItemBean("按钮二", TextViewFragment.newInstance()));
            button.add(new ItemBean("按钮三", VlayoutFragment.newInstance()));
            return button;
        }
        if ("对话框".equals(name)) {
            List<ItemBean> button = new ArrayList<>();
            button.add(new ItemBean("按钮一", SuperTextViewFragment.newInstance()));
            button.add(new ItemBean("按钮二", TextViewFragment.newInstance()));
            button.add(new ItemBean("按钮三", VlayoutFragment.newInstance()));
            return button;
        }

        return null;
    }


}
