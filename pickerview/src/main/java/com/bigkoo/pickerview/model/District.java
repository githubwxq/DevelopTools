package com.bigkoo.pickerview.model;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/4/1
 * @description 区
 */
public class District implements IAreaData{
    public String id;
    public String name;

    @Override
    public String toString() {
        return "District{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
