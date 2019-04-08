package com.wxq.commonlibrary.eventbus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/08
 * desc:
 * version:1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
      //为了标注某个方法 返回的枚举 发送的时候判断处理
      ThreadMode threadMode() default ThreadMode.PostThread;

}
