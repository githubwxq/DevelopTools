package com.bigkoo.pickerview.view;

import android.graphics.Typeface;
import android.view.View;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.bigkoo.pickerview.model.City;
import com.bigkoo.pickerview.model.District;
import com.bigkoo.pickerview.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/4/8
 * @description 省市区选择器的配置
 */
public class AreaWheelOptions {
    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;
    private List<Province> provinces = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<District> districts = new ArrayList<>();

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

    public AreaWheelOptions(View view, boolean linkage) {
        this.view = view;
        this.linkage = linkage;
        wv_option1 = (WheelView) view.findViewById(R.id.options1);// 初始化时显示的数据
        wv_option2 = (WheelView) view.findViewById(R.id.options2);
        wv_option3 = (WheelView) view.findViewById(R.id.options3);
    }

    public void setPicker(final List<Province> provinces) {
        this.provinces = provinces;
        wv_option1.setAdapter(new ArrayWheelAdapter<>(this.provinces));
        wv_option1.setCurrentItem(0);
        cities = this.provinces.get(0).city;
        wv_option2.setAdapter(new ArrayWheelAdapter<>(cities));
        wv_option2.setCurrentItem(0);
        districts = cities.get(0).area;
        wv_option3.setAdapter(new ArrayWheelAdapter<>(districts));
        wv_option3.setCurrentItem(0);
        wv_option1.setIsOptions(true);
        wv_option2.setIsOptions(true);
        wv_option3.setIsOptions(true);

        // 联动监听器
        wheelListener_option1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                if(index >= 0 && index < provinces.size()){
                    cities = provinces.get(index).city;
                    wv_option2.setAdapter(new ArrayWheelAdapter<>(cities));
                    wv_option2.setCurrentItem(0);
                    wheelListener_option2.onItemSelected(0);
                }
            }
        };
        wheelListener_option2 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                if(index >= 0 && index < cities.size()){
                    districts = cities.get(index).area;
                    wv_option3.setAdapter(new ArrayWheelAdapter<>(districts));
                    wv_option3.setCurrentItem(0);
                }
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

    public void setNPicker(final List<Province> provinces) {
        this.provinces = provinces;
        wv_option1.setAdapter(new ArrayWheelAdapter<>(this.provinces));
        wv_option1.setCurrentItem(0);
        cities = this.provinces.get(0).city;
        wv_option2.setAdapter(new ArrayWheelAdapter<>(cities));
        wv_option2.setCurrentItem(0);
        districts = cities.get(0).area;
        wv_option3.setAdapter(new ArrayWheelAdapter<>(districts));
        wv_option3.setCurrentItem(0);
        wv_option1.setIsOptions(true);
        wv_option2.setIsOptions(true);
        wv_option3.setIsOptions(true);
    }


    public void setTextContentSize(int textSize) {
        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
        wv_option3.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wv_option1.setTextColorOut(textColorOut);
        wv_option2.setTextColorOut(textColorOut);
        wv_option3.setTextColorOut(textColorOut);

    }

    private void setTextColorCenter() {
        wv_option1.setTextColorCenter(textColorCenter);
        wv_option2.setTextColorCenter(textColorCenter);
        wv_option3.setTextColorCenter(textColorCenter);

    }

    private void setDividerColor() {
        wv_option1.setDividerColor(dividerColor);
        wv_option2.setDividerColor(dividerColor);
        wv_option3.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wv_option1.setDividerType(dividerType);
        wv_option2.setDividerType(dividerType);
        wv_option3.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wv_option1.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_option2.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_option3.setLineSpacingMultiplier(lineSpacingMultiplier);

    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null){
            wv_option1.setLabel(label1);
        }
        if (label2 != null){
            wv_option2.setLabel(label2);
        }

        if (label3 != null){
            wv_option3.setLabel(label3);
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
        wv_option3.setCyclic(cyclic);
    }

    /**
     * 设置字体样式
     *
     * @param font 系统提供的几种样式
     */
    public void setTypeface(Typeface font) {
        wv_option1.setTypeface(font);
        wv_option2.setTypeface(font);
        wv_option3.setTypeface(font);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3 是否循环
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wv_option1.setCyclic(cyclic1);
        wv_option2.setCyclic(cyclic2);
        wv_option3.setCyclic(cyclic3);
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

        currentItems[1] = wv_option2.getCurrentItem() > (cities.size() - 1) ? 0 : wv_option2.getCurrentItem();

        currentItems[2] = wv_option3.getCurrentItem() > (districts.size() - 1) ? 0 : wv_option3.getCurrentItem();

        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        }
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
        wv_option3.setCurrentItem(option3);
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        cities = provinces.get(opt1Select).city;
        wv_option2.setAdapter(new ArrayWheelAdapter<>(cities));
        wv_option2.setCurrentItem(opt2Select);

        districts = cities.get(opt2Select).area;
        wv_option3.setAdapter(new ArrayWheelAdapter<>(districts));
        wv_option3.setCurrentItem(opt3Select);
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
        wv_option3.isCenterLabel(isCenterLabel);
    }
}
