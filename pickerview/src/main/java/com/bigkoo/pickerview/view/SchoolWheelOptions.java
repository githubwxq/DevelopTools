package com.bigkoo.pickerview.view;

import android.graphics.Typeface;
import android.view.View;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.bigkoo.pickerview.model.MyClass;
import com.bigkoo.pickerview.model.Grade;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nat.xue
 * @date 2017/10/23
 * @description  年级班级选择器的配置
 */

public class SchoolWheelOptions {
    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private List<Grade> grades = new ArrayList<>();
    private List<MyClass> classs = new ArrayList<>();
    private List<String> gradeStringList = new ArrayList<>();
    private List<String> classStringList = new ArrayList<>();

    private boolean linkage;
    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;

    //文字的颜色和分割线的颜色
    int textColorOut;
    int textColorCenter;
    int dividerColor;

    private WheelView.DividerType dividerType;

    // 条目间距倍数
    float lineSpacingMultiplier = 1.6F;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public SchoolWheelOptions(View view, boolean linkage) {
        this.view = view;
        this.linkage = linkage;
        wv_option1 = (WheelView) view.findViewById(R.id.options1);// 初始化时显示的数据
        wv_option2 = (WheelView) view.findViewById(R.id.options2);
    }

    public void setPicker(final List<Grade> grades) {
        this.grades = grades;
        gradeStringList.clear();
        for(Grade grade : grades){
            gradeStringList.add(grade.gradeName);
        }
        wv_option1.setAdapter(new ArrayWheelAdapter<>(this.gradeStringList));
        wv_option1.setCurrentItem(0);
        classs = this.grades.get(0).classList;
        classStringList.clear();
        for(MyClass myClass : classs){
            classStringList.add(myClass.sNickName);
        }
        wv_option2.setAdapter(new ArrayWheelAdapter<>(this.classStringList));
        wv_option2.setCurrentItem(0);
        wv_option1.setIsOptions(true);
        wv_option2.setIsOptions(true);

        // 联动监听器
        wheelListener_option1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                if(index >= 0 && index < grades.size()){
                    classs = grades.get(index).classList;
                    classStringList.clear();
                    for(MyClass myClass : classs){
                        classStringList.add(myClass.sNickName);
                    }
                    wv_option2.setAdapter(new ArrayWheelAdapter<>(classStringList));
                    wv_option2.setCurrentItem(0);
                    wheelListener_option2.onItemSelected(0);
                }
            }
        };

        wheelListener_option2 = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

            }
        };

        // 添加联动监听
        if (linkage) {
            wv_option1.setOnItemSelectedListener(wheelListener_option1);
        }
        if (linkage) {
            wv_option2.setOnItemSelectedListener(wheelListener_option2);
        }
    }

    public void setNPicker(final List<Grade> grades) {
        this.grades = grades;
        wv_option1.setAdapter(new ArrayWheelAdapter<>(this.grades));
        wv_option1.setCurrentItem(0);
        classs = this.grades.get(0).classList;
        wv_option2.setAdapter(new ArrayWheelAdapter<>(classs));
        wv_option2.setCurrentItem(0);
        wv_option1.setIsOptions(true);
        wv_option2.setIsOptions(true);
    }


    public void setTextContentSize(int textSize) {
        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wv_option1.setTextColorOut(textColorOut);
        wv_option2.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wv_option1.setTextColorCenter(textColorCenter);
        wv_option2.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wv_option1.setDividerColor(dividerColor);
        wv_option2.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wv_option1.setDividerType(dividerType);
        wv_option2.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wv_option1.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_option2.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     */
    public void setLabels(String label1, String label2) {
        if (label1 != null) {
            wv_option1.setLabel(label1);
        }
        if (label2 != null){
            wv_option2.setLabel(label2);
        }
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
    }

    /**
     * 设置字体样式
     *
     * @param font 系统提供的几种样式
     */
    public void setTypeface(Typeface font) {
        wv_option1.setTypeface(font);
        wv_option2.setTypeface(font);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2) {
        wv_option1.setCyclic(cyclic1);
        wv_option2.setCyclic(cyclic2);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2。
     * 在快速滑动未停止时，点击确定按钮，会进行判断，如果匹配数据越界，则设为0，防止index出错导致崩溃。
     *
     * @return 索引数组
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wv_option1.getCurrentItem();

        currentItems[1] = wv_option2.getCurrentItem() > (classs.size() - 1) ? 0 : wv_option2.getCurrentItem();

        return currentItems;
    }

    public void setCurrentItems(int option1, int option2) {
        if (linkage) {
            itemSelected(option1, option2);
        }
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
    }

    private void itemSelected(int opt1Select, int opt2Select) {
        classs = grades.get(opt1Select).classList;
        wv_option2.setAdapter(new ArrayWheelAdapter<>(classStringList));
        wv_option2.setCurrentItem(opt2Select);
    }

    /**
     * 设置间距倍数,但是只能在1.2-2.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */

    public void isCenterLabel(Boolean isCenterLabel) {
        wv_option1.isCenterLabel(isCenterLabel);
        wv_option2.isCenterLabel(isCenterLabel);
    }
}
