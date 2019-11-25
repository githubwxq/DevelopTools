package com.example.interviewdemo.glide2;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Lance
 * @date 2018/4/22
 */

public class Utils {

    private static final char[] HEX_CHAR_ARRAY = "0123456789abcdef".toCharArray();
    private static final char[] SHA_256_CHARS = new char[64];

    /**
     * 返回表示SHA-256结果的十六进制字符串。
     */
    public static String sha256BytesToHex(byte[] bytes) {
        synchronized (SHA_256_CHARS) {
            return bytesToHex(bytes, SHA_256_CHARS);
        }
    }

    // http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
    // /9655275#9655275
    private static String bytesToHex(byte[] bytes, char[] hexChars) {
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_CHAR_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_CHAR_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static <T> List<T> getSnapshot(Collection<T> other) {
        List<T> result = new ArrayList<>(other.size());
        for (T item : other) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}
