package com.bigkoo.pickerview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/4/1
 * @description 市
 */
public class City implements IAreaData{
    public String id;
    public String name;
    public List<District> area = new ArrayList<>();

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", area=" + area +
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
