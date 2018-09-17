package com.juziwl.uilibrary.multimedia;

import android.nfc.Tag;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rex on 2018/3/31.
 */

public class AACHelper {

    // 采样频率对照表
    private static Map<Integer, Integer> samplingFrequencyIndexMap = new HashMap<>();

    static {
        samplingFrequencyIndexMap.put(96000, 0);
        samplingFrequencyIndexMap.put(88200, 1);
        samplingFrequencyIndexMap.put(64000, 2);
        samplingFrequencyIndexMap.put(48000, 3);
        samplingFrequencyIndexMap.put(44100, 4);
        samplingFrequencyIndexMap.put(32000, 5);
        samplingFrequencyIndexMap.put(24000, 6);
        samplingFrequencyIndexMap.put(22050, 7);
        samplingFrequencyIndexMap.put(16000, 8);
        samplingFrequencyIndexMap.put(12000, 9);
        samplingFrequencyIndexMap.put(11025, 10);
        samplingFrequencyIndexMap.put(8000, 11);
        samplingFrequencyIndexMap.put(0x0, 96000);
        samplingFrequencyIndexMap.put(0x1, 88200);
        samplingFrequencyIndexMap.put(0x2, 64000);
        samplingFrequencyIndexMap.put(0x3, 48000);
        samplingFrequencyIndexMap.put(0x4, 44100);
        samplingFrequencyIndexMap.put(0x5, 32000);
        samplingFrequencyIndexMap.put(0x6, 24000);
        samplingFrequencyIndexMap.put(0x7, 22050);
        samplingFrequencyIndexMap.put(0x8, 16000);
        samplingFrequencyIndexMap.put(0x9, 12000);
        samplingFrequencyIndexMap.put(0xa, 11025);
        samplingFrequencyIndexMap.put(0xb, 8000);
    }

    private AdtsHeader mAdtsHeader = new AdtsHeader();
    private BitReader mHeaderBitReader = new BitReader(new byte[7]);
    private byte[] mSkipTwoBytes = new byte[2];
    private FileInputStream mFileInputStream;
    private byte[] mBytes = new byte[1024];

    /**
     * 构造函数，通过传递进来的文件路径创建输入流
     *
     * @param aacFilePath AAC文件路径
     * @throws FileNotFoundException
     */
    public AACHelper(String aacFilePath) throws FileNotFoundException {
        mFileInputStream = new FileInputStream(aacFilePath);
    }

    public static int getHeaderLength(String aacFilePath) {
        FileInputStream in = null;
        int len = 0;
        try {
            in = new FileInputStream(aacFilePath);
            AdtsHeader adtsHeader = new AdtsHeader();
            BitReader mHeaderBitReader = new BitReader(new byte[7]);
            if (in.read(mHeaderBitReader.buffer) < 7) {
                len = 0;
                return 0;
            }
            mHeaderBitReader.position = 0;
            int syncWord = mHeaderBitReader.readBits(12); // A
            if (syncWord != 0xfff) {
                throw new IOException("Expected Start Word 0xfff");
            }
            adtsHeader.mpegVersion = mHeaderBitReader.readBits(1); // B
            adtsHeader.layer = mHeaderBitReader.readBits(2); // C
            adtsHeader.protectionAbsent = mHeaderBitReader.readBits(1); // D

            len = adtsHeader.getSize();
            in.close();
        } catch (IOException e) {
            Log.e("AACHelper", "aac解析错误" + e.toString());
            e.printStackTrace();
        } finally {
            if (len == 0) {
                Log.e("AACHelper", "aac解析错误,头文件长度获取失败");
            } else {
                Log.e("AACHelper", "aac解析头部成功：len====" + len);
            }
            return len;
        }
    }


    /**
     * 获取下一Sample数据
     *
     * @param byteBuffer 存放Sample数据的ByteBuffer
     * @return 当前Sample的byte[]大小，如果为空返回-1
     * @throws IOException
     */
    public int getSample(ByteBuffer byteBuffer) throws IOException {
        if (readADTSHeader(mAdtsHeader, mFileInputStream)) {
            int length = mFileInputStream.read(mBytes, 0, mAdtsHeader.frameLength - mAdtsHeader.getSize());
            byteBuffer.clear();
            byteBuffer.put(mBytes, 0, length);
            byteBuffer.position(0);
            byteBuffer.limit(length);
            return length;
        }
        return -1;
    }


    /**
     * 从AAC文件流中读取ADTS头部
     *
     * @param adtsHeader      ADTS头部
     * @param fileInputStream AAC文件流
     * @return 是否读取成功
     * @throws IOException
     */
    private boolean readADTSHeader(AdtsHeader adtsHeader, FileInputStream fileInputStream) throws IOException {
        if (fileInputStream.read(mHeaderBitReader.buffer) < 7) {
            return false;
        }

        mHeaderBitReader.position = 0;

        int syncWord = mHeaderBitReader.readBits(12); // A
        if (syncWord != 0xfff) {
            throw new IOException("Expected Start Word 0xfff");
        }
        adtsHeader.mpegVersion = mHeaderBitReader.readBits(1); // B
        adtsHeader.layer = mHeaderBitReader.readBits(2); // C
        adtsHeader.protectionAbsent = mHeaderBitReader.readBits(1); // D
        adtsHeader.profile = mHeaderBitReader.readBits(2) + 1;  // E
        adtsHeader.sampleFrequencyIndex = mHeaderBitReader.readBits(4);
        adtsHeader.sampleRate = samplingFrequencyIndexMap.get(adtsHeader.sampleFrequencyIndex); // F
        mHeaderBitReader.readBits(1); // G
        adtsHeader.channelconfig = mHeaderBitReader.readBits(3); // H
        adtsHeader.original = mHeaderBitReader.readBits(1); // I
        adtsHeader.home = mHeaderBitReader.readBits(1); // J
        adtsHeader.copyrightedStream = mHeaderBitReader.readBits(1); // K
        adtsHeader.copyrightStart = mHeaderBitReader.readBits(1); // L
        adtsHeader.frameLength = mHeaderBitReader.readBits(13); // M
        adtsHeader.bufferFullness = mHeaderBitReader.readBits(11); // 54
        adtsHeader.numAacFramesPerAdtsFrame = mHeaderBitReader.readBits(2) + 1; // 56
        if (adtsHeader.numAacFramesPerAdtsFrame != 1) {
            throw new IOException("This muxer can only work with 1 AAC frame per ADTS frame");
        }
        if (adtsHeader.protectionAbsent == 0) {
            fileInputStream.read(mSkipTwoBytes);
        }
        return true;
    }

    /**
     * 释放资源
     *
     * @throws IOException
     */
    public void release() throws IOException {
        mFileInputStream.close();
    }
//    /**
//     * 添加ADTS头部
//     *
//     * @param packet    ADTS header 的 byte[]，长度为7
//     * @param packetLen 该帧的长度，包括header的长度
//     */
//    private void addADTStoPacket(byte[] packet, int packetLen) {
//        int profile = 2; // AAC LC
//        int freqIdx = 3; // 48000Hz
//        int chanCfg = 2; // 2 Channel
//
//        packet[0] = (byte) 0xFF;
//        packet[1] = (byte) 0xF9;
//        packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
//        packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
//        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
//        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
//        packet[6] = (byte) 0xFC;
//    }

}
