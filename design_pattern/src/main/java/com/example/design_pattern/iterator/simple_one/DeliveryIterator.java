package com.example.design_pattern.iterator.simple_one;

//快递迭代类
    public class DeliveryIterator implements Iterator {
        private Aggregate aggregate;//容器对象
        private int index;//当前索引

        public DeliveryIterator(Aggregate aggregate) {
            this.aggregate = aggregate;//初始化容器对象
        }

        @Override
        public boolean hasNext() {//是否存在下一条记录
            if (index < aggregate.size()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object next() {//返回当前记录并移到下一条记录
            return aggregate.get(index++);
        }
    }