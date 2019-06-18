package com.example.interviewdemo.glide2.load.codec;


import com.example.interviewdemo.glide2.cache.ArrayPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lance
 * @date 2018/4/22
 * <p>
 * 装饰者模式 和代理非常相似
 * 一个类A 代理类B 装饰类C
 * 代理模式下，更关心A的功能，使用B限制A
 * 装饰者模式下，更关心C功能，使用C扩展功能
 */
public class MarkInputStream extends InputStream {



    private ArrayPool arrayPool;
    //64K
    int BUFFER_SIZE_BYTES = 64 * 1024;

    private InputStream in;
    private int markPos = -1;
    private int pos;
    private int readCount;

    //缓存池
    private byte[] buf;

    public MarkInputStream(InputStream in, ArrayPool arrayPool) throws IOException {
        this.in = in;
        //数组复用池
        this.arrayPool = arrayPool;
        buf = arrayPool.get(BUFFER_SIZE_BYTES);
    }


    @Override
    public int read() throws IOException {
        //被reset 小于已读 从buf中拿
        if (pos < readCount) {
            return buf[pos++];
        }

        int b = in.read();
        //没有更多数据
        if (b == -1) {
            return b;
        }
        //是否有足够空间存储
        if (pos >= buf.length) {
            resizeBuf(0);
        }
        buf[pos++] = (byte) b;
        readCount++;
        return b;
    }

    private void resizeBuf(int len) {
        int newLen = buf.length * 2 + len;
        byte[] newbuf = arrayPool.get(newLen);
        //拷贝数据
        System.arraycopy(buf, 0, newbuf, 0, buf.length);
        //加入数组池
        arrayPool.put(buf);
        buf = newbuf;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        //需要读取的总长度
        int count = len - off;
        //buf中的有效数据
        int availables = readCount - pos;
        //满足读取的需求
        if (availables >= count) {
            //复制数据
            System.arraycopy(buf, pos, b, off, count);
            pos += count;
            return count;
        }
        //先将buf中的数据读进b
        if (availables > 0) {
            System.arraycopy(buf, pos, b, off, availables);
            off += availables;
            pos += availables;
        }
        //还需要读取的数据长度 从原inputstream读取
        count = len - off;
        int readlen = in.read(b, off, count);
        if (readlen == -1) {
            return readlen;
        }
        //没有足够的空间存放本次数据
        int i = pos + readlen - buf.length;
        if (i > 0) {
            resizeBuf(i);
        }
        System.arraycopy(b, off, buf, pos, readlen);
        pos += readlen;
        //记录已经读取的总长度
        readCount += readlen;
        return readlen;
    }

    @Override
    public synchronized void reset() throws IOException {
        pos = markPos;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public synchronized void mark(int readlimit) {
        markPos = pos;
    }

    @Override
    public void close() throws IOException {
        if (buf != null) {
            arrayPool.put(buf);
            buf = null;
        }
        in.close();
    }

    public void release() {
        if (buf != null) {
            arrayPool.put(buf);
            buf = null;
        }
    }
}
