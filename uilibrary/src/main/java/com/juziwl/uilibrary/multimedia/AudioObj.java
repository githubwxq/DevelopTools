package com.juziwl.uilibrary.multimedia;

import java.io.Serializable;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/26
 * desc:存放录音对象
 * version:1.0
 */
public class AudioObj implements Serializable{
    public AudioObj(String audioPath, String audioName, long timeLength) {
        this.audioPath = audioPath;
        this.audioName = audioName;
        this.timeLength = timeLength;
    }

    public AudioObj(String audioName) {
        this.audioName = audioName;
    }
    public AudioObj() {

    }

    public String audioPath;
    public String audioName;
    public long timeLength;

}
