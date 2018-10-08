package com.wxq.commonlibrary.channel;

import com.wxq.commonlibrary.channel.data.Apk;
import com.wxq.commonlibrary.channel.data.Eocd;
import com.wxq.commonlibrary.channel.data.V2SignBlock;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class DNApkParser {


    public static Apk parser(File file) throws Exception {
        Apk apk = new Apk();
        apk.setFile(file);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        //默认没有任何注释信息  22个字节
        ByteBuffer eocd = findEocd(randomAccessFile, Constants.EOCD_COMMENT_OFFSET);
        if (null == eocd) {
            eocd = findEocd(randomAccessFile, Constants.EOCD_COMMENT_OFFSET + Constants.EOCD_MAX_LEN);
        }
        apk.setEocd(new Eocd(eocd));
        //判断签名版本
        //zip当中第二部分开始的下标

        int cdoffset = apk.getEocd().getCdoffset();

        V2SignBlock v2block = findV2(randomAccessFile, cdoffset);
        apk.setV2SignBlock(v2block);
        //没有找到v2签名块
        if (null == v2block) {
            //是否使用v1签名
            apk.setV1(isV1(file));
        } else {
            apk.setV2(true);
        }
        randomAccessFile.close();
        return apk;
    }


    private static boolean isV1(File file) throws Exception {
        JarFile jarFile = new JarFile(file);
        JarEntry manifest = jarFile.getJarEntry("META-INF/MANIFEST.MF");
        if (null == manifest) {
            return false;
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().matches("META-INF/\\w+\\.SF")) {
                return true;
            }
        }
        return false;
    }

    private static V2SignBlock findV2(RandomAccessFile file, int index) throws Exception {
        int block_2size_offset = index - 24;
        file.seek(block_2size_offset);
        //可能存在的v2前面magic 和 size
        ByteBuffer v2BlockMagicBuffer = ByteBuffer.allocate(24);
        v2BlockMagicBuffer.order(ByteOrder.LITTLE_ENDIAN);
        file.read(v2BlockMagicBuffer.array());
        long block2_size = v2BlockMagicBuffer.getLong();
        byte[] block_magic = new byte[16];

        v2BlockMagicBuffer.get(block_magic);
        //判断magic是否相等
        if (Arrays.equals(block_magic, Constants.APK_SIGNING_BLOCK_MAGIC)) {
            file.seek(((int) index - block2_size - 8));
            ByteBuffer v2BlockBuffer = ByteBuffer.allocate((int) (block2_size + 8));
            v2BlockBuffer.order(ByteOrder.LITTLE_ENDIAN);
            file.read(v2BlockBuffer.array());
            if (v2BlockBuffer.getLong() == block2_size) {
                //key=id value
                Map pair = new LinkedHashMap<Integer, ByteBuffer>();
                while (v2BlockBuffer.position() < v2BlockBuffer.capacity() - 8 - 16) {
                    //读取8字节的 id-value总长度
                    long id_value_size = v2BlockBuffer.getLong();
                    //id值
                    int id = v2BlockBuffer.getInt();
                    //value
                    ByteBuffer value = ByteBuffer.allocate((int) id_value_size - 4);
                    value.order(ByteOrder.LITTLE_ENDIAN);
                    v2BlockBuffer.get(value.array());
                    pair.put(id, value);
                }
                //包含v2签名的id
                if (pair.containsKey(Constants.V2_ID)) {
                    v2BlockBuffer.flip();
                    return new V2SignBlock(v2BlockBuffer, pair);
                }
            }
        }

        return null;
    }

    private static ByteBuffer findEocd(RandomAccessFile randomAccessFile, int offset) throws Exception {
        randomAccessFile.seek((int) (randomAccessFile.length() - offset));
        ByteBuffer eocd_buffer = ByteBuffer.allocate(offset);
        eocd_buffer.order(ByteOrder.LITTLE_ENDIAN);
        randomAccessFile.read(eocd_buffer.array());
        for (int current_eocd_offset = 0; current_eocd_offset + Constants.EOCD_COMMENT_OFFSET <= offset; ++current_eocd_offset) {
            int eocd_tag = eocd_buffer.getInt(current_eocd_offset);
            if (Constants.EOCD_TAG == eocd_tag) {
                int comment_index = current_eocd_offset + Constants.EOCD_COMMENT_LEN_OFFSET;
                //注释内容的长度
                short comment_len = eocd_buffer.getShort(comment_index);
                //寻找到了正确的 eocd
                if (comment_len == offset - comment_index - 2) {
                    byte[] array = eocd_buffer.array();
                    //将查找到的eocd拷贝出来
                    ByteBuffer eocd = ByteBuffer.allocate(offset - current_eocd_offset);
                    eocd.order(ByteOrder.LITTLE_ENDIAN);
                    System.arraycopy(array, current_eocd_offset, eocd.array(), 0, eocd.capacity());

                    return eocd;
                }
            }
        }
        return null;
    }
}
