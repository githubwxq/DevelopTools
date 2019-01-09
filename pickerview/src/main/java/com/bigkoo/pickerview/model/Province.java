package com.bigkoo.pickerview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/4/1
 * @description 省
 */
public class Province implements IAreaData{
    public String id;
    public String name;
    public List<City> city = new ArrayList<>();

    @Override
    public String toString() {
        return "Province{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city=" + city +
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
