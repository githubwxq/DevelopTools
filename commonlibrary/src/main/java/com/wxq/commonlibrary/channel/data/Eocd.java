package com.wxq.commonlibrary.channel.data;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Eocd {

    private ByteBuffer data;

    public Eocd(ByteBuffer data ){
        this.data = data;
    }
    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public ByteBuffer getData() {
        return data;
    }

    public int getCdoffset(){
        return data.getInt(16);
    }


    //获得第二部分长度
    public int getCdSize(){
        return data.getInt(12);
    }


}
