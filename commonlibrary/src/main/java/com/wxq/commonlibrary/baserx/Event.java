package com.wxq.commonlibrary.baserx;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/5/1
 * @description RxBus传递的数据和类型
 */
public class Event {

    public Event(int action) {
        this.action = action;
    }

    public Event(int action, Object object) {
        this.action = action;
        this.object = object;
    }

    public Event(int action, Object[] object) {
        this.action = action;
        this.objectArr = object;
    }

    public int action;

    public Object object;

    public Object[] objectArr;

    public Object[] getObjectArr() {
        return objectArr;
    }

    public <T extends Object> T getObject() {
        return (T) object;
    }

    public int position;

}
