package com.wxq.commonlibrary.bmob;

import com.wxq.commonlibrary.baserx.Event;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/24
 * desc: im模块产生的所有事件 其他模块需要监听im模块事件可以而外写事件
 * version:1.0
 */
public class BmobImEvent extends Event {
    /**
     * Im登入成功事件
     */
    public static final int ImLOGINSUCCESS=1000;
    /**
     * Im收到新消息事件
     */
    public static final int RECEIVENEWMESSAGE = 1001;

   /**
     * Imh会话列表更新
     */
    public static final int UPDATECONVERRSION = 1002;

   /**
     * Im朋友列表刷新
     */
    public static final int UPDATEFRIENDLIST = 1003;


    public BmobImEvent(int action) {
        super(action);
    }
}
