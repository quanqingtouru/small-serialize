package com.quanqingtouru.serialize.small.util;

import com.quanqingtouru.serialize.small.core.Codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CodecUtil {
    /*
     * 第一字节最高位为1表示需要计算长度,为零表示为null
     * 第一字节次高位为1表示本字节前四位用以表示需要多少个字节来表示长度, 为0表示使用本字节前6位即可表示长度，
     * */
    public static byte[] length2Stream(int length) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int b0 = (1 << 7) | (1 << 6);
        if (length < (1 << 6)) {
            outputStream.write(length | (1 << 7));
        } else if (length < (1 << 8)) {
            outputStream.write(1 | b0);
            outputStream.write(length);
        } else if (length < 1 << 17) {
            outputStream.write(2 | b0);
            int b1 = length & 0xff;
            int b2 = length >>> 8;
            outputStream.write(b1);
            outputStream.write(b2);
        } else if (length < 1 << 25) {
            outputStream.write(3 | b0);
            int b1 = length & 0xff;
            int b2 = length >>> 8;
            int b3 = length >>> 16;
            outputStream.write(b1);
            outputStream.write(b2);
            outputStream.write(b3);
        } else {
            outputStream.write(4 | b0);
            int b1 = length & 0xff;
            int b2 = length >>> 8;
            int b3 = length >>> 16;
            int b4 = length >>> 24;
            outputStream.write(b1);
            outputStream.write(b2);
            outputStream.write(b3);
            outputStream.write(b4);
        }

        return outputStream.toByteArray();
    }

    /**
     * if the second bit is zero, then current byte could calculate the length of the string, its range is 0~63
     * if the second bit is zero, then first 4 bits could calculate how many bytes need to record the length of the string
     * <p>
     * if we need one byte, the length range is 0-2^8 - 1
     * if we need two bytes, the length range is 0-2^16 -1
     * if we need three bytes, the length range is 0-2^24 -1
     * if we need four bytes, the length range is 0-2^32 - 1
     */
    public static int stream2Length(InputStream inputStream, int b0) throws IOException {
        if (((b0 >> 6) & 1) == 0) {
            return b0 & 0x7f;
        }

        int byteCount = b0 & 0xf;

        if (byteCount == 1) {
            return inputStream.read();
        }

        if (byteCount == 2) {
            int b1 = inputStream.read();
            int b2 = inputStream.read();
            return b1 + (b2 << 8);
        }

        if (byteCount == 3) {
            int b1 = inputStream.read();
            int b2 = inputStream.read();
            int b3 = inputStream.read();
            return b1 + (b2 << 8) + (b3 << 16);
        }

        int b2 = inputStream.read();
        int b3 = inputStream.read();
        int b4 = inputStream.read();
        int b5 = inputStream.read();

        return b2 + (b3 << 8) + (b4 << 16) + (b5 << 24);
    }

    public static boolean same(byte[] src, byte[] dest) {
        if (src.length != dest.length) {
            return false;
        }

        for (int i = 0; i < src.length; i++) {
            if (src[i] != dest[i]) {
                return false;
            }
        }

        return true;
    }
}
