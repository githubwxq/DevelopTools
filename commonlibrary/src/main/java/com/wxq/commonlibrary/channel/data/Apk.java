package com.wxq.commonlibrary.channel.data;

import java.io.File;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Apk {

    //记录apk文件
    private File file;

    private Eocd eocd;

    private V2SignBlock v2SignBlock;
    //是否使用v1签名
    private boolean v1;

    private boolean v2;

    public void setV2SignBlock(V2SignBlock v2SignBlock) {
        this.v2SignBlock = v2SignBlock;
    }

    public V2SignBlock getV2SignBlock() {
        return v2SignBlock;
    }

    public boolean isV1() {
        return v1;
    }

    public boolean isV2() {
        return v2;
    }

    public void setV1(boolean v1) {
        this.v1 = v1;
    }

    public void setV2(boolean v2) {
        this.v2 = v2;
    }

    public void setEocd(Eocd eocd) {
        this.eocd = eocd;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Eocd getEocd() {
        return eocd;
    }

    public File getFile() {
        return file;
    }
}
