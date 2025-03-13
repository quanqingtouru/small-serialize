package com.quanqingtouru.serialize.small.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CodecInteger extends Codec<Integer> {
    @Override
    public void encode(Integer object, OutputStream outputStream) throws IOException {
        int v = object;
        byte[] writeBuffer = new byte[4];
        writeBuffer[0] = (byte) (v >>> 24);
        writeBuffer[1] = (byte) (v >>> 16);
        writeBuffer[2] = (byte) (v >>> 8);
        writeBuffer[3] = (byte) (v);
        outputStream.write(writeBuffer);
    }

    @Override
    public Integer decode(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[4];
        int read = inputStream.read(bytes);
        if (read != 4) {
            throw new IOException("read length is not 4");
        }
        return (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }
}
