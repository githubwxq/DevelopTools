package com.wxq.commonlibrary.channel.data;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class V2SignBlock {

    ByteBuffer data;
    Map<Integer,ByteBuffer> pair;
    public V2SignBlock(ByteBuffer data, Map<Integer,ByteBuffer> pair){
        this.data = data;
        this.pair = pair;
    }

    public ByteBuffer getData() {
        return data;
    }

    public Map<Integer, ByteBuffer> getPair() {
        return pair;
    }
}
