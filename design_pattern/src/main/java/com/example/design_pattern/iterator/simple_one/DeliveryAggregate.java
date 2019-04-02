package com.example.design_pattern.iterator.simple_one;

import java.util.ArrayList;
import java.util.List;

//快递容器类
    public class DeliveryAggregate implements Aggregate {
        private List<String> list = new ArrayList<>();//内部使用list来存储数据

        public int size() {//容器大小
            return list.size();
        }

        public String get(int location) {
            return list.get(location);
        }

        public void add(String tel) {
            list.add(tel);
        }

        public void remove(String tel) {
            list.remove(tel);
        }

        @Override
        public Iterator iterator() {////返回迭代器
            return new DeliveryIterator(this);
        }
    }