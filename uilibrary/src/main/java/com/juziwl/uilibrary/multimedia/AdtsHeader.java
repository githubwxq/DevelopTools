package com.juziwl.uilibrary.multimedia;

/**
 * Created by Rex on 2018/3/31.
 */

public class AdtsHeader {
    int getSize() {
        return 7 + (protectionAbsent == 0 ? 2 : 0);
    }

    int protectionAbsent;
    int sampleFrequencyIndex;
    int mpegVersion;
    int layer;
    int profile;
    int sampleRate;
    int channelconfig;
    int original;
    int home;
    int copyrightedStream;
    int copyrightStart;
    int frameLength;
    int bufferFullness;
    int numAacFramesPerAdtsFrame;
}
