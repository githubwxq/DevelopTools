package com.wxq.commonlibrary.baserx;





import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/5/1
 * @description 用于替换EventBus的RxBus实现
 */
public class RxBus {
    private final FlowableProcessor<Object> bus;

    private RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final RxBus sInstance = new RxBus();
    }

    public void post(Event event) {
        bus.onNext(event);
    }

    public Flowable<Event> take() {
        return bus.toSerialized().ofType(Event.class);
    }
}
