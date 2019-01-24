package com.wxq.commonlibrary.bmob;

import com.wxq.commonlibrary.baserx.Event;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/24
 * desc:
 * version:1.0
 */
public class BmobImEvent extends Event {
    /**
     * Im登入成功实践
     */
    public static final int ImLOGINSUCCESS=1000;


    public BmobImEvent(int action) {
        super(action);
    }
}
